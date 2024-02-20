package edu.uw.ischool.ryanng20.quizdroid

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor
import java.util.concurrent.Executors

interface TopicRepository {
    fun getAllTopics(): List<Topic>
    fun getTopic(topicName: String): Topic?
    fun downloadTopics(url: String, callback: (Boolean) -> Unit)
}

class TopicRepositoryStorage(context: Context, url: String) : TopicRepository {
    private val TAG: String = "TopicRepositoryStorage"
    private val topics = mutableListOf<Topic>()
    private val assets = context.assets

    init {
        Log.i(TAG, "Constructing $TAG")
        downloadTopics(url) {}

    }

    override fun downloadTopics(url: String, callback: (Boolean) -> Unit) {
        val executor: Executor = Executors.newSingleThreadExecutor()
        executor.execute {
            val connectUrl = URL(url).openConnection() as HttpURLConnection
            val inputStream: InputStream = connectUrl.inputStream
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            try {
                topics.clear()
                val json = JSONArray(jsonString)
                val topicExecutor: Executor = Executors.newSingleThreadExecutor()
                topicExecutor.execute {
                    for (i in 0..<json.length()) {
                        val item = json.getJSONObject(i)
                        val questions = item.getJSONArray("questions")
                        val questionList = mutableListOf<Question>()
                        for(j in 0..<questions.length()) {
                            val currQuestion = questions.getJSONObject(j)
                            val questionAnswers = currQuestion.getJSONArray("answers")
                            val questionOptions = (0..<questionAnswers.length()).map { questionAnswers.getString(it) }
                            val questionObj = Question(
                                currQuestion.getString("text"),
                                questionOptions,
                                currQuestion.getInt("answer") - 1
                            )

                            questionList.add(questionObj)
                        }
                        val topicObj = Topic(
                            item.getString("title"),
                            item.getString("desc"),
                            item.getString("desc"),
                            questionList
                        )
                        topics.add(topicObj)
                    }
                    inputStream.close()
                    callback(true)
                }
            } catch (e: JSONException) {
                Log.e(TAG, "invalid JSON input, $e")
                callback(false)
            }
        }
    }

    override fun getAllTopics(): List<Topic> {
        return topics
    }

    override fun getTopic(topicName: String): Topic? {
        return topics.find { it.title == topicName }
    }
}
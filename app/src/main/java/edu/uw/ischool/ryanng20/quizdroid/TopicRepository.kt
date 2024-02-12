package edu.uw.ischool.ryanng20.quizdroid

import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.InputStream

interface TopicRepository {
    fun getAllTopics(): List<Topic>
}

class TopicRepositoryStorage(context: Context) : TopicRepository {
    private val TAG: String = "TopicRepositoryStorage"
    private val topics = mutableListOf<Topic>()
    private val assets = context.assets

    init {
        Log.i(TAG, "Constructing $TAG")
        val inputStream: InputStream = assets.open("quiz_questions.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val json = JSONObject(jsonString)
        val quizTopics = json.getJSONArray("topics")

        for (i in 0..<quizTopics.length()) {
            val item = quizTopics.getJSONObject(i)
            val questions = item.getJSONArray("questions")
            val questionList = mutableListOf<Question>()
            for(j in 0..<questions.length()) {
                val currQuestion = questions.getJSONObject(j)
                val questionAnswers = currQuestion.getJSONArray("answers")
                val questionOptions = (0..<questionAnswers.length()).map { questionAnswers.getString(it) }
                val questionObj = Question(
                    currQuestion.getString("question"),
                    questionOptions,
                    currQuestion.getInt("correctAnswer")
                )

                questionList.add(questionObj)
            }
            val topicObj = Topic(
                item.getString("name"),
                item.getString("shortDescription"),
                item.getString("longDescription"),
                questionList
            )

            topics.add(topicObj)
        }
        inputStream.close()

    }

    override fun getAllTopics(): List<Topic> {
        return topics
    }
}
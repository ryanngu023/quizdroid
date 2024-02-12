package edu.uw.ischool.ryanng20.quizdroid

import android.content.Context
import android.util.Log
interface TopicRepository {
    fun getAllTopics(): List<Topic>
}

class TopicRepositoryStorage(context: Context) : TopicRepository {
    private val TAG: String = "TopicRepositoryStorage"
    private val topics = mutableListOf<Topic>()
    private val assets = context.assets

    init {
        Log.i(TAG, "Constructing $TAG")

    }

    override fun getAllTopics(): List<Topic> {
        TODO("Not yet implemented")
    }
}
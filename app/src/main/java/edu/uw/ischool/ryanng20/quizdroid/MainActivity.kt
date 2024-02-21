package edu.uw.ischool.ryanng20.quizdroid


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.util.Log
import android.widget.TextView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var firstBtn: Button
    private lateinit var secondBtn: Button
    private lateinit var thirdBtn: Button
    private lateinit var firstDesc: TextView
    private lateinit var secondDesc: TextView
    private lateinit var thirdDesc: TextView
    private lateinit var quizApp: QuizApp
    private lateinit var topicRepository: TopicRepository
    private lateinit var timedExecutor: ScheduledExecutorService

    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "Creating main Activity")
        firstBtn = findViewById(R.id.firstBtn)
        secondBtn = findViewById(R.id.secondBtn)
        thirdBtn = findViewById(R.id.thirdBtn)
        firstDesc = findViewById(R.id.firstBtnSubtitle)
        secondDesc = findViewById(R.id.secondBtnSubtitle)
        thirdDesc = findViewById(R.id.thirdBtnSubtitle)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        if(Settings.System.getInt(this.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0) {
            Log.i(TAG, Settings.System.getInt(this.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0).toString())
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No Internet Connection")
            builder.setMessage("Please turn off airplane mode.")
            builder.setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            builder.setPositiveButton("Open") { dialog, _ ->
                val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
                startActivity(intent)
                dialog.dismiss()
            }
            builder.show()
        } else if(!networkAvailable()) {
            Log.i(TAG, "error")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No Signal")
            builder.setMessage("Please connect to the Internet")
            builder.setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            val networkDialog = builder.create()
            networkDialog.show()
        } else {
            Log.i(TAG, "item loaded")
            quizApp = application as QuizApp
            topicRepository = quizApp.topicRepository


            val executor: Executor = Executors.newSingleThreadExecutor()
            executor.execute {
                    downloadTopics()
                }


            timedExecutor = Executors.newSingleThreadScheduledExecutor()
            timedExecutor.scheduleAtFixedRate({
                downloadTopics()
            }, 0L, quizApp.dllCheck * 1L, TimeUnit.MINUTES)
        }

    }

    private fun downloadTopics() {
        topicRepository.downloadTopics(quizApp.url) {
            if (it) {
                Log.i(TAG, topicRepository.getAllTopics().toString())
                runOnUiThread {
                    val topicList = topicRepository.getAllTopics()
                    Log.i(TAG, topicList.toString())
                    firstDesc.text = topicList[0].shortDesc
                    secondDesc.text = topicList[1].shortDesc
                    thirdDesc.text = topicList[2].shortDesc
                    firstBtn.text = topicList[0].title
                    secondBtn.text = topicList[1].title
                    thirdBtn.text = topicList[2].title

                    Log.i(TAG, "Within UI THREAD: $topicList")
                    firstBtn.setOnClickListener {
                        Log.i(TAG, "First Clicked")
                        startTopicOverview(topicList[0].title)
                    }
                    secondBtn.setOnClickListener {
                        Log.i(TAG, "Second Clicked")
                        startTopicOverview(topicList[1].title)
                    }
                    thirdBtn.setOnClickListener {
                        Log.i(TAG, "Third Clicked")
                        startTopicOverview(topicList[2].title)
                    }
                    Toast.makeText(this@MainActivity, "Downloading from ${quizApp.url}", Toast.LENGTH_SHORT).show()
                }

            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Download Failed")
                builder.setMessage("Downloaded has failed, please try again.")
                builder.setNegativeButton("Quit") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                builder.setPositiveButton("Retry") { dialog, _ ->
                    downloadTopics()
                    dialog.dismiss()
                }
            }
        }
    }

    private fun networkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetwork
        return netInfo != null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.i(TAG, "creating options menu")
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(TAG, "${item.title} selected")
        val intent = Intent(this, PreferencesActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    private fun startTopicOverview(topic: String) {
        val intent = Intent(this, TopicOverviewActivity::class.java)
        intent.putExtra("topic", topic)

        startActivity(intent)
    }

}
package com.animebiru.kerjaaja.presentation.ui.activity.main

import android.content.res.AssetFileDescriptor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.animebiru.kerjaaja.R
import com.animebiru.kerjaaja.data.sources.preferences.DataStorePreferences
import com.animebiru.kerjaaja.databinding.ActivityMainBinding
import com.animebiru.kerjaaja.databinding.FragmentChangeThemeBinding
import com.animebiru.kerjaaja.domain.events.UIEvent
import com.animebiru.kerjaaja.domain.events.UserEvent
import com.animebiru.kerjaaja.domain.utils.ExtensionsHelper.collectLatestFlowWhenStarted
import com.animebiru.kerjaaja.domain.utils.viewBindings
import com.animebiru.kerjaaja.presentation.ui.dialog.DialogError
import com.animebiru.kerjaaja.presentation.ui.register.RegisterFragmentDirections
import com.animebiru.kerjaaja.presentation.viewmodels.ChangeThemeViewModel
import com.animebiru.kerjaaja.presentation.viewmodels.EventViewModel
import com.animebiru.kerjaaja.presentation.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBindings(ActivityMainBinding::bind)
    private val eventViewModel: EventViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    @Inject lateinit var dataStorePreferences: DataStorePreferences
    private val switchViewModel: ChangeThemeViewModel by viewModels()
    private val dialogError by lazy { DialogError() }
    private val navHost by lazy { supportFragmentManager.findFragmentById(R.id.fcvMainNav) as NavHostFragment }
    private val navController by lazy { navHost.navController }
    private var shouldKeepSplashScreenOnScreen: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { shouldKeepSplashScreenOnScreen }
        lifecycleScope.launch {
            delay(3000)
            shouldKeepSplashScreenOnScreen = false
        }
        super.onCreate(savedInstanceState)

        getThemeSetting()

        userViewModel.events.observe(this) { event ->
            Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "onCreate: event: ${event::class.java.simpleName}")
            when (event) {
                is UserEvent.OnError -> showError(event.errorMessage)
                is UserEvent.OnLoading -> showLoading()
                is UserEvent.OnLogin -> onLogin()
                is UserEvent.OnLogout -> redirectToLoginPage()
                is UserEvent.OnRegisterSuccess -> redirectToLoginPage(event.username)
                is UserEvent.OnIdle -> closeLoading(false)
                else -> Unit
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                eventViewModel.uIEvent.collect {
                    when (it) {
                        is UIEvent.OnComplete -> closeLoading(false)
                        is UIEvent.OnError -> showError(it.errorMessage)
                        is UIEvent.OnLoading -> showLoading()
                    }
                }
            }
        }
//        runModel()
    }

    //////////////////////////////////////////////////

    private fun runModel() {
//     Contoh pemanggilan fungsi predictJob
        val statement = "Saya dapat melakukan klasifikasi menggunakan support vector machines"
//        val statement = "klasifikasi support vector machines"
      val predictedJob = predictJob(statement)
        Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "runModel: $predictedJob")
    }

    // Fungsi untuk memanggil model TensorFlow Lite
    private fun predictJob(statement: String): String {
        // Preprocess input
        val preprocessedStatement = preprocessInput(listOf(statement))
        val flattenPreprocessedStatement = preprocessedStatement.flatten()

        // Load the TensorFlow Lite model
        val interpreter = Interpreter(loadModelFile())

        // Allocate input and output buffers
//        val inputBuffer = ByteBuffer.allocateDirect(preprocessedStatement.size * 4)
//            .order(ByteOrder.nativeOrder())
        val inputBuffer = ByteBuffer.allocateDirect(flattenPreprocessedStatement.size * 4)
            .order(ByteOrder.nativeOrder())
//        val outputBuffer = ByteBuffer.allocateDirect(320 * 4)
//            .order(ByteOrder.nativeOrder())
        val outputBuffer = ByteBuffer.allocateDirect(320 * 4)
            .order(ByteOrder.nativeOrder())
// Prepare input buffer
        inputBuffer.rewind()
        Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "predictJob: inputBuffer capacity -> ${inputBuffer.capacity()}")
        Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "predictJob: outputBuffer capacity -> ${outputBuffer.capacity()}")
        Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "predictJob: preprocessedStatement.flatten() size -> ${preprocessedStatement.flatten().size}")
        flattenPreprocessedStatement.forEach {
            inputBuffer.putFloat(it)
            Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "predictJob: float added -> $it")
        }

        // Run inference
        interpreter.run(inputBuffer, outputBuffer)

        // Get predicted job label
//        val predictedLabel = outputBuffer.floatArray.indexOf(outputBuffer.floatArray.maxOrNull())
        val predictedLabel = outputBuffer.asFloatBuffer().array().indexOfFirst { it == outputBuffer.asFloatBuffer().array().maxOrNull() }

        // Get job mapping
        val jobMapping = mapOf(
            -1 to "Unknown",
            0 to "Machine learning",
            1 to "Data Analis",
            2 to "Apoteker",
            3 to "Bartender",
            4 to "Arsitek",
            5 to "Koki"
        ) // Update with your job mapping

        // Get predicted job
        val predictedJob = jobMapping[predictedLabel]

        return predictedJob ?: "Unknown"
    }

    // Fungsi untuk memuat file model TensorFlow Lite
    private fun loadModelFile(): ByteBuffer {
        // Load the model file
        val assetFileDescriptor: AssetFileDescriptor = assets.openFd("nlc_classifier.tflite")
        val fileDescriptor = assetFileDescriptor.fileDescriptor

        val fileInputStream = FileInputStream(fileDescriptor)
        val fileChannel = fileInputStream.channel
//        val startOffset = (0).toLong()
//        val declaredLength = fileChannel.size()
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "loadModelFile: startOffset -> $startOffset")
        Log.d("MY_DEBUG:${this@MainActivity.javaClass.simpleName}", "loadModelFile: declaredLength -> $declaredLength")
//        val modelByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset.toLong(), declaredLength)
        val modelByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        fileChannel.close()
        fileInputStream.close()

        return modelByteBuffer
    }

    // Fungsi untuk memproses input
    private fun preprocessInput(texts: List<String>): List<List<Float>> {
        // Convert text to lowercase and tokenize
        val sequences = texts.map { text ->
            text.lowercase().split(" ")
        }

        // Perform any other preprocessing steps here (e.g., tokenization, padding, etc.)

        // Convert sequences to list of list of floats
        val processedSequences = sequences.map { sequence ->
            sequence.map { word ->
//                word.toFloat()
                (word.hashCode() % Float.MAX_VALUE)
            }
        }

        return processedSequences
    }

    /////////////////////////////////////////////////

    private fun onLogin() {
        lifecycleScope.launch {
            val isNewUser = !dataStorePreferences.getBoardingStatus().first()
            if (isNewUser) redirectToOnBoarding() else redirectToHomePage()
            dataStorePreferences.setBoardingStatus(false)
        }
    }

    private fun redirectToOnBoarding() {
        navController.navigate(R.id.action_loginFragment_to_boardingFragment)
        closeLoading()
    }

    private fun redirectToLoginPage(username: String? = null) {
        if (username == null) {
            val navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
            navController.navigate(R.id.loginFragment, Bundle(), navOptions)
        } else {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(username)
            navController.navigate(action)
        }
        closeLoading()
    }

    private fun redirectToHomePage() {
        navController.navigate(R.id.action_loginFragment_to_homeFragment)
        closeLoading()
    }

    private fun closeLoading(notify: Boolean = true) {
        binding.cpiCircularProgress.visibility = View.GONE
        binding.fcvMainNav.visibility = View.VISIBLE
        if (notify) userViewModel.notifyTransactionSuccess()
    }

    private fun showLoading() {
        binding.cpiCircularProgress.visibility = View.VISIBLE
        binding.fcvMainNav.visibility = View.GONE
    }

    fun showError(errorMessage: String) {
        dialogError.setMessage(errorMessage)
        dialogError.showDialog(supportFragmentManager)
        closeLoading()
    }

    private fun getThemeSetting(){
        val bindingSetting = FragmentChangeThemeBinding.inflate(layoutInflater)
        switchViewModel.getThemeSettings().observe(this){ isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                bindingSetting.changeTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                bindingSetting.changeTheme.isChecked = false
            }
        }

        bindingSetting.changeTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            switchViewModel.saveThemeSetting(isChecked)
        }
    }

}
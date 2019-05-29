package com.weiggle.github.first

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_live_data_map_example.*

class LiveDataMapExampleActivity : AppCompatActivity() {

    companion object {
        fun getMapLiveData(callingClassContext: Context) =
            Intent(callingClassContext, LiveDataMapExampleActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_map_example)

        val viewModel = ViewModelProviders.of(this).get(TransformationViewModel::class.java)
        viewModel.userAddedData.observe(this,
            Observer<String> { t -> Snackbar.make(root, t!!, Snackbar.LENGTH_SHORT).show() })

        add.setOnClickListener {
            hideInput()
            viewModel.addNewUser(User(edit.text.toString()))
        }
    }

     fun hideInput() {
        val imm =  getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val v = window!!.peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.windowToken, 0);
        }
    }

}

class TransformationViewModel : ViewModel() {
    private val userLiveData = MutableLiveData<User>()

    val userAddedData: LiveData<String> = Transformations.map(userLiveData, this::someFunc)

    private fun someFunc(user: User) = "New user ${user.username} added to database!"

    fun addNewUser(user: User) = apply {
        userLiveData.value = user
    }

}

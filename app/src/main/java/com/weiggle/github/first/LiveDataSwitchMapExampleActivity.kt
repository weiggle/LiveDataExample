package com.weiggle.github.first

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_live_data_switch_map_example.*

class LiveDataSwitchMapExampleActivity : AppCompatActivity() {

    companion object {
        fun getSwitchMapLiveData(callingClassContext: Context) = Intent(callingClassContext, LiveDataSwitchMapExampleActivity::class.java)
    }

    private val userList: MutableList<User> = ArrayList()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_switch_map_example)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        userAdapter = UserAdapter( this, userList)
        recycler_view.adapter = userAdapter
        val viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel.userNameResult.observe(this, Observer {
            if (userList.isNotEmpty()) {
                userList.clear()
            }
            userList.addAll(it)
            userAdapter.notifyDataSetChanged()
        })

        add.setOnClickListener {
            viewModel.searchUserByName(edit.text.toString())
        }

    }
}


class UserAdapter(context: Context, private val userList: List<User>): RecyclerView.Adapter<UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,null, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.text.text = userList[position].username
    }
}

class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val text = itemView.findViewById<TextView>(R.id.text)
}

class UserViewModel: ViewModel() {
    private val query = MutableLiveData<String>()

    private val userRepo = UserRepo()

    val userNameResult: LiveData<List<User>> = Transformations.switchMap(query) {
        userRepo.searchUserWithName(it)
    }

    fun searchUserByName(name: String) = apply { query.value = name }


    fun test() {
        val name = searchUserByName("li")
    }
}


class UserRepo {
    private val userList: MutableList<User> = ArrayList()

    init {
        for (i in 1..100) {
            userList.add(User("Hello user $i"))
        }
    }

    fun searchUserWithName(name: String): LiveData<List<User>> {
        val searchUserList = ArrayList<User>()
        for (user in userList) {
            if (user.username.contains(regex = Regex(name))){
                searchUserList.add(user)
            }
        }
        val temp = MutableLiveData<List<User>>()
        temp.value = searchUserList
        return temp
    }
}
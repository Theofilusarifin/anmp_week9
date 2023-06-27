package com.example.a160420046_todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.a160420046_todoapp.R
import com.example.a160420046_todoapp.model.Todo
import com.example.a160420046_todoapp.util.NotificationHelper
import com.example.a160420046_todoapp.util.ToDoWorker
import com.example.a160420046_todoapp.viewmodel.DetailTodoViewModel
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)


        btnAdd.setOnClickListener {
//            NotificationHelper(view.context)
//                .createNotification("Todo Created", "A new todo has been created! Stay focus!")

            val myWorkRequest = OneTimeWorkRequestBuilder<ToDoWorker>()
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setInputData(workDataOf(
                    "title" to "ToDo Created",
                    "message" to  "A new todo has been created! Stay focus!"
                ))
                .build()
            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)

            val txtTitle = view.findViewById<EditText>(R.id.txtTitle)
            val txtNotes = view.findViewById<EditText>(R.id.txtNotes)
            var radio = view.findViewById<RadioGroup>(R.id.radioGroupPriority)
            var radioButton = view.findViewById<RadioButton>(radio.checkedRadioButtonId)

            var todo = Todo(txtTitle.text.toString(), txtNotes.text.toString(), radioButton.tag.toString().toInt())
            val list = listOf(todo)
            viewModel.addTodo(list)
            Toast.makeText(view.context, "Data added", Toast.LENGTH_LONG).show()
            Navigation.findNavController(it).popBackStack()
        }


    }
}
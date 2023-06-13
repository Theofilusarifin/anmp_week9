package com.example.a160420046_todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.a160420046_todoapp.R
import com.example.a160420046_todoapp.viewmodel.DetailTodoViewModel
import com.google.android.material.textfield.TextInputEditText

class EditTodoFragment : Fragment() {

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
        viewModel = ViewModelProvider(this)[DetailTodoViewModel::class.java]

        val txtJudulTodo = view.findViewById<TextView>(R.id.txtJudulTodo)
        val txtNotes = view.findViewById<TextView>(R.id.txtNotes)
        val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)

        txtJudulTodo.text = "Edit Todo"
        btnAdd.text = "Save Changes"

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)
        observeViewModel()

        btnAdd.setOnClickListener{
            var radio = view.findViewById<RadioGroup>(R.id.radioGroupPriority)
            var radioButton = view.findViewById<RadioButton>(radio.checkedRadioButtonId)

            viewModel.update(txtTitle.text.toString(), txtNotes.text.toString(), radioButton.tag.toString().toInt(), uuid)

            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }

    fun observeViewModel(){
        val txtTitle = view?.findViewById<TextInputEditText>(R.id.txtTitle)
        val txtNotes = view?.findViewById<TextInputEditText>(R.id.txtNotes)

        var low = view?.findViewById<RadioButton>(R.id.radioLow)
        var medium = view?.findViewById<RadioButton>(R.id.radioMedium)
        var high = view?.findViewById<RadioButton>(R.id.radioHigh)

        viewModel.todoLD.observe(viewLifecycleOwner, Observer{
            txtTitle?.setText(it.title)
            txtNotes?.setText(it.notes)

            when (it.priority){
                1 -> low?.isChecked = true
                2 -> medium?.isChecked = true
                else -> high?.isChecked = true
            }
        })
    }


}
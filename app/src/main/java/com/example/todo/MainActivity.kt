package com.example.todo
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.*
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var itemList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        addButton = findViewById(R.id.addButton)
        listView = findViewById(R.id.listView)

        itemList = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_checked, itemList)
        listView.adapter = adapter

        addButton.setOnClickListener {
            val item = editText.text.toString()
            if (item.isNotEmpty()) {
                itemList.add(item)
                adapter.notifyDataSetChanged()
                editText.text.clear()
                Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter an item !", Toast.LENGTH_SHORT).show()
            }
        }

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, view, position, _ ->
            val checkedTextView = view as CheckedTextView
            checkedTextView.isChecked = !checkedTextView.isChecked
        }

        listView.onItemLongClickListener = AdapterView.OnItemLongClickListener {  parent, view, position, id ->
         val item = parent.getItemAtPosition(position) as String
            val confirmMessage = "Are you sure you want to delete this \"$item\" ?"
            showConfirmationDialog(confirmMessage) {
                itemList.removeAt(position)
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "$item deleted successfully", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
    private fun showConfirmationDialog(message: String, onConfirm: () -> Unit) {
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, _ ->
                onConfirm.invoke()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.show()
    }
}

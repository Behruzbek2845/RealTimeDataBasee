package com.example.realtimedatabase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.realtimedatabase.adapters.RvAdapter
import com.example.realtimedatabase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("Matnlar")

        binding.btnSend.setOnClickListener {
            var text = binding.edt1.text.toString()
            reference.child(reference.push().key!!).setValue(text)
            binding.edt1.text = null
        }
        
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<String>()
                val children = snapshot.children
                for (child in children){
                    val text = child.value
                    list.add(text.toString())
                }
                binding.rv.adapter = RvAdapter(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
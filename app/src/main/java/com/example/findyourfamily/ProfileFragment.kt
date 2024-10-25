package com.example.findyourfamily


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.findyourfamily.databinding.FragmentProfileBinding
import com.example.findyourfamily.view.LoginActivity
import com.example.findyourfamily.view.MainActivity
import com.example.findyourfamily.viewmodel.AuthenticationViewmodel
import com.example.findyourfamily.viewmodel.FireStoreViewmodel
import com.example.findyourfamily.viewmodel.LocationViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var authViewModel: AuthenticationViewmodel
    private lateinit var firestoreViewModel: FireStoreViewmodel
    private lateinit var locationViewModel: LocationViewModel
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)

        authViewModel = ViewModelProvider(this).get(AuthenticationViewmodel::class.java)
        firestoreViewModel = ViewModelProvider(this).get(FireStoreViewmodel::class.java)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)


        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))

        }

        binding.homeBtn.setOnClickListener {
            startActivity(Intent(this.context, MainActivity::class.java))
        }

        loadUserInfo()
        binding.updateBtn.setOnClickListener {
            val newName = binding.NameEt.text.toString()
            val newLocation = binding.Loaction.text.toString()

            updateBtn(newName, newLocation)
        }

        return binding.root
    }

    private fun updateBtn(newName: String, newLocation: String) {
        val currentUser = authViewModel.getCurrentUser()
        if (currentUser != null) {
            val userId = currentUser.uid
            firestoreViewModel.updateUser(requireContext(), userId, newName, newLocation)
            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), MainActivity::class.java))
        } else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserInfo() {
        val currentUser = authViewModel.getCurrentUser()
        if(currentUser != null) {
            binding.emailEt.setText(currentUser.email)
            firestoreViewModel.getUser(requireContext(), currentUser.uid){ user ->
                if (currentUser.displayName != null) {
                    binding.NameEt.setText(currentUser.displayName)
                }
            }
        }else {
            Toast.makeText(requireContext(), "User not found", Toast.LENGTH_SHORT).show()
        }

    }
}
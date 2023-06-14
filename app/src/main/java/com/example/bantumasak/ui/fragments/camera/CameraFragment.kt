package com.example.bantumasak.ui.fragments.camera

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.bantumasak.R
import com.example.bantumasak.databinding.FragmentCameraBinding
import com.example.bantumasak.ml.ConvertedModelFoodClassificationV3
import com.example.bantumasak.rotateBitmap
import com.example.bantumasak.ui.activity.camerax.CameraxActivity
import com.example.bantumasak.ui.activity.main.MainActivity
import com.example.bantumasak.uritoBitmap
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File

class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //permission check
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        setView()
    }

    private fun setView() {
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent()){
                it.let {imageUri ->
                    //Change image format
                    val imageBitmap = uritoBitmap(requireContext().contentResolver, imageUri!!)
                    processImage(requireContext(), imageBitmap)

                    binding?.cameraIngredientsImg?.setImageURI(it)
                }
            }
        binding?.cameraOpenGallery?.setOnClickListener {
            getImage.launch("image/*")
        }
        binding?.cameraTakePhoto?.setOnClickListener {
            val intent = Intent(requireActivity(), CameraxActivity::class.java)
            launcherIntentCameraX.launch(intent)
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra("picture")
            } as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )
            processImage(requireContext(), result)
            binding?.cameraIngredientsImg?.setImageBitmap(result)
        }
    }

    private fun processImage(context: Context, bitmap: Bitmap) {
        //Resize image
        val imageResize = ImageProcessor.Builder()
            .add(ResizeOp(227, 227, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        //Labels
        val labels = context.assets.open("labels.txt").bufferedReader().readLines()

        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        tensorImage = imageResize.process(tensorImage)

        val model = ConvertedModelFoodClassificationV3.newInstance(context)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 227, 227, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

        var maxIndex = 0
        outputFeature0.forEachIndexed { index, fl ->
            if (outputFeature0[maxIndex] < fl) {
                maxIndex = index
            }
        }
        binding?.cameraResult?.text = labels[maxIndex]

        model.close()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    requireContext(),
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}
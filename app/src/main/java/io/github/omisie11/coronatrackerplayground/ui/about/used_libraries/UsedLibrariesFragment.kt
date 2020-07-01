package io.github.omisie11.coronatrackerplayground.ui.about.used_libraries

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.github.omisie11.coronatrackerplayground.R
import io.github.omisie11.coronatrackerplayground.databinding.FragmentUsedLibrariesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class UsedLibrariesFragment : Fragment(), UsedLibrariesAdapter.OnItemClickListener {

    private var _binding: FragmentUsedLibrariesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewAdapter: UsedLibrariesAdapter
    private val moshi: Moshi by inject()
    private val usedLibsJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + usedLibsJob)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsedLibrariesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewAdapter = UsedLibrariesAdapter(this)
        binding.recyclerViewLibs.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = viewAdapter
        }

        uiScope.launch {
            val usedLibsList = async(Dispatchers.Default) {
                getUsedLibraries()
            }
            withContext(Dispatchers.Main) {
                val libs = usedLibsList.await()
                if (libs.isNotEmpty()) viewAdapter.setData(libs)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerViewLibs.adapter = null
        _binding = null

        usedLibsJob.cancel()
    }

    override fun onItemClicked(usedLibraryRepoUrl: String) {
        openWebUrl(usedLibraryRepoUrl)
    }

    private fun getUsedLibraries(): List<UsedLibrary> {
        val objectString: String =
            requireActivity().resources.openRawResource(R.raw.used_libraries).bufferedReader()
                .use { it.readText() }
        val libsListType = Types.newParameterizedType(List::class.java, UsedLibrary::class.java)
        val jsonAdapter = moshi.adapter<List<UsedLibrary>>(libsListType)
        return jsonAdapter.fromJson(objectString) ?: emptyList()
    }

    private fun openWebUrl(urlAddress: String) {
        if (urlAddress.isNotEmpty()) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlAddress)))
        }
    }
}

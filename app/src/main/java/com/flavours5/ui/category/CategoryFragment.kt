package com.flavours5.ui.category

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.flavours5.R
import com.flavours5.adapters.CategoryAdapter
import com.flavours5.common.Progress
import com.flavours5.common.Utility
import com.flavours5.models.CategoryResponse
import com.flavours5.models.SubCategoryResponse
import com.flavours5.webservices.APIClient
import com.flavours5.webservices.OnResponseInterface
import com.flavours5.webservices.ResponseListner
import com.google.gson.Gson


class CategoryFragment : Fragment(), OnResponseInterface {
    private lateinit var expandableListView: RecyclerView
    private val process = Progress()
    private lateinit var dialog: Dialog
    private var subCatListAll: MutableList<SubCategoryResponse> = ArrayList()
    private lateinit var hashMap: HashMap<Int, MutableList<SubCategoryResponse.DataBean>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_category, container, false)

        intiView(root)
        return root

    }

    private fun intiView(root: View?) {
        expandableListView = root!!.findViewById(R.id.categoryList)
        expandableListView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        subCatListAll = ArrayList()
        hashMap = HashMap()
        getCategoryData()
    }

    private fun getCategoryData() {
        process.show(requireContext(), "Please Wait...")
        val call = APIClient().apiInterface.callCategoryData()
        ResponseListner(this).getResponse(call,context)
    }

    private var categoryList: MutableList<CategoryResponse.DataBean> = ArrayList()

    override fun onApiResponse(response: Any?) {
        if (response != null) {
            process.dialog.dismiss()
            Log.d("TAG", "onApiResponse: " + Gson().toJson(response))
            if (response is CategoryResponse) {
                if (categoryList!=null)
                    categoryList.clear()

                if (response.data != null) {
                    categoryList = response.data
                    for (categoryList in response.data) {
                        getSubCategoryData(categoryList.id)
                    }
                    setAdapter(categoryList, hashMap)

                }
            } else if (response is SubCategoryResponse) {
                if (subCatListAll!=null)
                    subCatListAll.clear()
                if (response.data != null) {
                    val subCategoryResponse: SubCategoryResponse = response
                    for (x in subCategoryResponse.data) {
                        hashMap[x.categoryid] = response.data
                    }

                    subCatListAll.add(subCategoryResponse)
                    Log.d("TAG", "getSubCategoryData: " + subCatListAll.size)
                    expandableListView.adapter = adapter

                }
            }
        }
        Log.d("TAG", "getSubCategoryData: " + subCatListAll.size)

    }

    private fun getSubCategoryData(id: Int) {
        val call = APIClient().apiInterface.callSubCat(id)
        ResponseListner(this).getResponse(call,context)
    }


    private lateinit var adapter: CategoryAdapter
    private fun setAdapter(
        categoryList: List<CategoryResponse.DataBean>,
        subCatListAll: HashMap<Int, MutableList<SubCategoryResponse.DataBean>>
    ) {

        adapter = context?.let {
            CategoryAdapter(
                categoryList as MutableList<CategoryResponse.DataBean>, requireContext(),
                subCatListAll
            )
        }!!
        if (adapter != null)
            adapter.notifyDataSetChanged()

    }

    override fun onApiFailure(message: String?) {
        process.dialog.dismiss()
        dialog = Utility.createErrorDialog(context, getString(R.string.error))!!
        dialog.show()
    }

}

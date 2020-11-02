
package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.application.image.lib_ui.R
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.activity.*
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.activity.multi.ChooseMultipleItemUseTypeActivity
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.activity.node.ChooseNodeUseTypeActivity
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.HomeAdapter
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.HomeEntity
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.BaseQuickAdapter
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.listener.OnItemClickListener




class HomeActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityHomeBinding

    /**
     * RV适配器
     */
    private val homeAdapter by lazy {
        HomeAdapter(homeItemData).apply {
            animationEnable = true

            val top = layoutInflater.inflate(R.layout.top_view, binding.recyclerView, false)
            addHeaderView(top)
            setOnItemClickListener(this@HomeActivity)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = adapter.data[position] as HomeEntity
        if (!item.isHeader) {
            startActivity(Intent(this@HomeActivity, item.activity))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = homeAdapter
    }

    private val homeItemData: ArrayList<HomeEntity>
        get() = arrayListOf(
                HomeEntity(headerTitle = "BaseQuickAdapter 基础功能"),
                HomeEntity("Animation", AnimationUseActivity::class.java, R.mipmap.gv_animation),
                HomeEntity("Header/Footer", HeaderAndFooterUseActivity::class.java, R.mipmap.gv_header_and_footer),
                HomeEntity("EmptyView", EmptyViewUseActivity::class.java, R.mipmap.gv_empty),
                HomeEntity("ItemClick", ItemClickActivity::class.java, R.mipmap.gv_item_click),
                HomeEntity("DataBinding", DataBindingUseActivity::class.java, R.mipmap.gv_databinding),
                HomeEntity("DiffUtil", DiffUtilActivity::class.java, R.mipmap.gv_databinding),

                HomeEntity(headerTitle = "Adapter 类型"),
                HomeEntity("MultipleItem", ChooseMultipleItemUseTypeActivity::class.java, R.mipmap.gv_multipleltem),
                HomeEntity("Quick Section", SectionQuickUseActivity::class.java, R.mipmap.gv_section),
                HomeEntity("Node", ChooseNodeUseTypeActivity::class.java, R.mipmap.gv_expandable),

                HomeEntity(headerTitle = "功能模块"),
                HomeEntity("LoadMore", LoadMoreRefreshUseActivity::class.java, R.mipmap.gv_pulltorefresh),
                HomeEntity("DragAndSwipe", DragAndSwipeUseActivity::class.java, R.mipmap.gv_drag_and_swipe),
                HomeEntity("UpFetch", UpFetchUseActivity::class.java, R.drawable.gv_up_fetch)

//                HomeEntity("MultipleDragAndSwipe", null, R.mipmap.gv_drag_and_swipe),
//                HomeEntity("SectionMultipleItem", null, R.mipmap.gv_multipleltem)
        )
}
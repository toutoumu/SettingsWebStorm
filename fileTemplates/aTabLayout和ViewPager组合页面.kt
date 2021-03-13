#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
#parse("File Header.java")
import android.os.Bundle
import butterknife.OnClick
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.library.base.fragments.CommonTabLayoutFragment
import java.io.Serializable
import com.library.base.activitys.CommonActivity
import com.library.base.fragments.BaseFragment

class ${NAME} : CommonTabLayoutFragment() {
  
  override fun getTitle() = "我的订单"
  
  // 分页参数初始化
  private val items = arrayListOf<PageItem>().apply {
    add(PageItem("全部", 1))
    add(PageItem("未付款", 2))
  }
  
  companion object {
    const val DATA = "data"

    /**
     * 启动当前页面
     * @param id 详情主键
     * @param baseFragment [BaseFragment]
     * @param requestCode 请求码
     */
    @JvmStatic
    fun start(id: Long, baseFragment: BaseFragment, requestCode: Int = -1) {
      baseFragment.startActivity(CommonActivity::class.java, ${NAME}::class.java, Bundle().apply {
        putLong(DATA, id)
      }, requestCode)
    }
  }

  private var id: Long? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    arguments?.let {
      if (arguments!!.containsKey(DATA)) {
        id = arguments?.getLong(DATA)
        obtainData(true)
      }
    }
  }

  // todo 根据需要选择模式
  // override fun tabLayoutMode() = TabLayout.MODE_SCROLLABLE

  override fun getItem(position: Int): Fragment {
    return OrderListFragment().apply {
      this.arguments = Bundle().apply {
        putInt(OrderListFragment.ORDER_TYPE, items[position].type)
      }
    }
  }

  override fun getCount(): Int {
    return items.size
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return items[position].title
  }
}

/**
 * 分页参数
 */
class PageItem(val title: String, val type: Int) : Serializable
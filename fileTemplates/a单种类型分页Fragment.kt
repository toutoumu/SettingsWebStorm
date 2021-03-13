#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
#parse("File Header.java")
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.library.base.recyclerview.RecyclerViewAdapter
import com.library.base.recyclerview.base.RecyclerViewHolder
import com.library.base.recyclerview.wrapper.LoadMoreWrapper
import butterknife.OnClick
import com.beijing.base.CommonPageListFragment
import com.library.base.fragments.BaseFragment
import com.library.base.activitys.CommonActivity
import io.reactivex.Observable

class ${NAME} : CommonPageListFragment<User>() {
  
  override fun getTitle() =  "${NAME}"
  
  companion object {
    const val DATA = "data"
    const val PAGE_TYPE ="page_type"

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
    
    @JvmStatic
    fun instance(pageType: Int): ${NAME} {
      return ${NAME}().apply {
        arguments = Bundle().apply {
          putInt(PAGE_TYPE, pageType)
        }
      }
    }
  }

  private var id: Long? = null
  private var pageType = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    arguments?.let {
      if(arguments!!.containsKey(PAGE_TYPE)){
        pageType = arguments!!.getInt(PAGE_TYPE)
      }
      if (arguments!!.containsKey(DATA)) {
        id = arguments?.getLong(DATA)
      }
    }
    obtainData(true)
  }

  override fun createAdapter(): Adapter<*> {
    // 分割线
    val decoration = DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL)
    decoration.setDrawable(getDrawable(R.drawable.divider))
    mRecyclerView.addItemDecoration(decoration)

    mRecyclerView.layoutManager = LinearLayoutManager(context)

    // 数据项
    val ad = object : RecyclerViewAdapter<User>(mActivity, R.layout.item_test, mData) {
      override fun convert(holder: RecyclerViewHolder, item: User, position: Int) {
        
      }
    }

    // 加载更多
    return LoadMoreWrapper(ad, this@${NAME})
  }

  override fun getRequest(isRefresh: Boolean, page: Int, pageSize: Int): Observable<Model<PageData<User>>> {
    return Api.create(TestApi::class.java).userlist("", "")
  }
}
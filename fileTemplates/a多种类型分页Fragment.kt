#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
#parse("File Header.java")
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.library.base.recyclerview.MultiItemTypeAdapter
import com.library.base.recyclerview.base.ItemViewDelegate
import com.library.base.recyclerview.base.RecyclerViewHolder
import com.library.base.recyclerview.wrapper.LoadMoreWrapper
import butterknife.OnClick
import com.beijing.base.CommonPageListFragment
import com.library.base.activitys.CommonActivity
import com.library.base.fragments.BaseFragment
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
    // 分隔线
    val decoration = DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL)
    decoration.setDrawable(getDrawable(R.drawable.divider))
    mRecyclerView.addItemDecoration(decoration)

    // 我发送的
    val ad = MultiItemTypeAdapter<User>(mActivity, mData)
    ad.addItemViewDelegate(object : ItemViewDelegate<User> {
      override fun getItemViewLayoutId() = R.layout.item_test

      override fun isForViewType(item: User, position: Int): Boolean {
        return true
      }

      override fun convert(holder: RecyclerViewHolder, item: User, position: Int) {
        holder.setText(R.id.text, data.content)
        Glide.with(mActivity)
          .load(data.image)
          .apply(RequestOptions().circleCrop())
          .into(holder.getView(R.id.image))
      }
    })

    // 别人发送的
    ad.addItemViewDelegate(object : ItemViewDelegate<User> {
      override fun getItemViewLayoutId() = R.layout.item_test

      override fun isForViewType(item: User, position: Int): Boolean {
        return true
      }

      override fun convert(holder: RecyclerViewHolder, item: User, position: Int) {
        holder.setText(R.id.text, data.content)
        Glide.with(mActivity)
          .load(data.image)
          .apply(RequestOptions().circleCrop())
          .into(holder.getView(R.id.image))
      }
    })

    // 加载更多
    return LoadMoreWrapper(ad, this)
  }

  override fun getRequest(isRefresh: Boolean, page: Int, pageSize: Int): Observable<Model<PageData<User>>> {
    return Api.create(TestApi::class.java)
      .userlist("", "")
  }
}
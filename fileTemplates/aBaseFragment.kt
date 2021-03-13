#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
#parse("File Header.java")
import android.os.Bundle
import android.view.View
import butterknife.OnClick
import com.library.base.activitys.CommonActivity
import com.library.base.fragments.BaseFragment
import com.library.base.fragments.LoadingStatus
import com.library.base.fragments.LoadingStatus.SUCCESS
import io.reactivex.Observable

class ${NAME} : BaseFragment() {
  
  override fun getTitle() =  "${NAME}"
  
  override fun getContentLayoutResourceId() = R.layout.content_test

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
  }

  @OnClick(R.id.confirm)
    fun onViewClick(view: View) {
        when (view.id) {
          R.id.confirm -> { 
              // 处理点击事件
          }
        }
  }
}
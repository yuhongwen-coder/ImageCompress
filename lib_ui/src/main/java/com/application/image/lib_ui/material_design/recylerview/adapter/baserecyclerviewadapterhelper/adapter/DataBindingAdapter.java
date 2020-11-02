package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter;



import com.application.image.lib_ui.R;
import com.application.image.lib_ui.databinding.ItemMovieBinding;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.Movie;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.MoviePresenter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.BaseQuickAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.viewholder.BaseDataBindingHolder;


import org.jetbrains.annotations.NotNull;

/**
 * @author: limuyang
 * @date: 2019-12-05
 * @Description: DataBinding Adapter
 *
 */
public class DataBindingAdapter extends BaseQuickAdapter<Movie, BaseDataBindingHolder<ItemMovieBinding>> {

    private MoviePresenter mPresenter = new MoviePresenter();

    public DataBindingAdapter() {
        super(R.layout.item_movie);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemMovieBinding> holder, Movie item) {
        // 获取 Binding
        ItemMovieBinding binding = holder.getDataBinding();
        if (binding != null) {
            binding.setMovie(item);
            binding.setPresenter(mPresenter);
            binding.executePendingBindings();
        }
    }
}

package com.example.photomanagement

import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.example.photomanagement.databinding.ImageContainerBinding


class ImageAdapter(val ClickListener:ImageListener,val ImageList: ArrayList<Image> =ArrayList<Image>()) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    class ViewHolder(binding: ImageContainerBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ImageContainerBinding
        init {
            this.binding = binding
        }
    }

    class ImageDiffCallback:DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
          return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id==newItem.id
        }

    }
    class ImageListener(val clickListener: (image: Image) -> Unit) {
        fun onClick(image: Image) = clickListener(image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ImageContainerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ImageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewHolder=holder as ViewHolder
        val Image=ImageList.get(position)

        val bitmap = MediaStore.Images.Thumbnails.getThumbnail(holder.binding.imageView.context.contentResolver,
            Image.id,
            MediaStore.Images.Thumbnails.MINI_KIND, null)
        viewHolder.binding.imageView.setImageBitmap(bitmap)
        viewHolder.binding.imageView.setOnClickListener { ClickListener.onClick(Image) }
    }

    fun submitList(list: ArrayList<Image>) {
        ImageList.clear()
        ImageList.addAll(list)
        notifyDataSetChanged()
    }


}

import React, {useContext, useEffect, useState} from 'react'
import AnimationWrapper from '../common/page-animation'
import {toast, Toaster} from 'react-hot-toast'
import {EditorContext} from '../pages/editor.pages'
import Tag from './tags.component'
import {UserContext} from '../App'
import {useNavigate, useParams} from 'react-router-dom'
import {createBlog, getCategories} from "../coreApi/CoreApi.js";
import Dropdown from "./dropdown.jsx";
import {HandleError} from "../common/HandleError.jsx";

const PublishForm = () => {
  const charLength = 200
  const tagLimit = 10
  const navigate = useNavigate()
  const {blog_id} = useParams()
  const [categories, setCategories] = useState([]);
  let {blog: {banner, title, tags, des, content,images, category_id}, setEditorState, setBlog,blog} = useContext(EditorContext)
  // let {userAuth: {authenticationToken}} = useContext(UserContext)
  let {userAuth: {authenticationToken}} = useContext(UserContext)
  let userAuth  =   useContext(UserContext);

  useEffect(() => {
    const fetchData = async () => {
      try{
        const categories =await getCategories();
        console.log(categories);
        setCategories(categories);
      }
      catch(error){
        console.log(error);
        toast.error("Unable to fetch category list");
      }
    }
    fetchData();
  },[] )

  const handleClose = () =>{
    setEditorState("editor")
  }
  const handleSelect = (value) => {
    console.log(value)
    setBlog({...blog, category_id: value})
    console.log(blog)
  }

  const handleBlogTitleChange = (e) =>{
    let input = e.target;
    setBlog({...blog, title: input.value});

  }
  const handleKeyDown = (e) =>{
    if(e.keyCode === 13 || e.keyCode === 188){
      e.preventDefault();
      let tag = e.target.value;
      if(tags.length < tagLimit){
        if(!tags.includes(tag) && tag.length){
          setBlog({...blog, tags: [...tags, tag]})
        }
      }
      else{
        toast.error(`You can add max ${tagLimit} tags`)
      }
      e.target.value = ""
    }
  }
  const handlePublish = async (e) => {
    if (e.target.className.includes('disable'))
      return

    if (!title.length)
      return toast.error("Write Blog Title befor publising")

    if (!des.length || des.length > charLength)
      return toast.error(`Write a description about your blog within ${charLength} characters to publish`)
    if (!tags.length || tags.length > 10)
      return toast.error(`Write some tags about blog within ${tagLimit} taglimit to publish`)

    if(!category_id)
      return toast.error(`A category id is mandatory to publish a blog`)
    //this is not working
    // setBlog({ ...blog, category_id: selectedCategory })

    let loadingToast = toast.loading("Publishing...")
    let blogObj = {title, banner, des, content, tags, draft: false, category_id}
    console.log(blogObj)
    e.target.classList.add('disable');
    try {
      console.log(blog)
      const res = await createBlog({blogObj, blog_id, authenticationToken});
      e.target.classList.remove('disable');
      toast.dismiss(loadingToast)
      toast.success("Published successfully");
      setTimeout(() => {
        navigate('/dashboard/blogs')
      }, 0);
    } catch (error) {
      console.log(error)
      e.target.classList.remove('disable');
      toast.dismiss(loadingToast)
      await HandleError(error, navigate);
    }
  }

  return (
      <AnimationWrapper >
        <section className='w-screen min-h-screen grid items-center lg:grid-cols-2 py-16 lg:gap-4'>
          <Toaster/>
          <button className='w-12 h-12 absolute right-[5vw] z-10 top-[5%] lg:top-[10%]' onClick={handleClose}><i className='fi fi-br-cross'></i></button>
          <div className='max-w-[500px] center '>
            <p className='text-dark-grey mb-1'>Preview</p>
            <div className='w-full aspect-video rounded-lg overflow-hidden bg-grey mt-4'>
              <img src={banner} alt="Banner" />
            </div>
            <h1 className='text-4xl font-medium mt-2 leading-tight line-clamp-2'>{title}</h1>
            <p className='font-gelasio line-clamp-3 text-xl leading-7 mt-4'>{des}</p>
          </div>
          <div className='border-grey lg:border-1 lg:pl-4'>
            <p className='text-dark-grey mb-2 mt-9'>Blog Title</p>
            <input type="text" placeholder='Blog Title' defaultValue={title} className='input-box pl-4' onChange={handleBlogTitleChange}/>

            <Dropdown
                options={{categories: categories, default: category_id}}
                callback = {handleSelect}
            />

            <p className='text-dark-grey mb-2 mt-9'>Short description about your blog</p>
            <textarea
                maxLength={charLength}
                defaultValue={des}
                className='h-40 resize-none leading-7 input-box pl-4'
                onChange={(e) => setBlog({...blog, des: e.target.value})}
                onKeyDown={(e) => {
                  if(e.keyCode === 13)
                    e.preventDefault()
                }}
            >

          </textarea>
            <p className='mt-1 text-dark-grey text-sm text-right'>
              {charLength - des.length} characters left
            </p>

            <p className='text-dark-grey mb-2 mt-9'>Topics - ( Helps in searching and ranking your blog post )</p>
            <div className='relative input-box pl-2 py-2 pb-4'>
              <input type="text" placeholder='Topics' onKeyDown={handleKeyDown} className='sticky input-box bg-white top-0 lef0 pl-4 mb-3 focus:bg-white' />
              {
                  tags && tags.map((tag, i) =>{
                    return <Tag key={i} tagIndex = {i} tag={tag}/>
                  })
              }
            </div>
            <p className='mt-1 pt-1 mb-4 text-dark-grey text-right '>{tagLimit - tags.length} Tags left</p>
            <button onClick={handlePublish} className='btn-dark px-8 '>Publish</button>
          </div>
        </section>
      </AnimationWrapper>
  )
}

export default PublishForm;
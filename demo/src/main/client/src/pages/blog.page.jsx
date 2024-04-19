import axios from 'axios'
import React, { createContext, useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import AnimationWrapper from '../common/page-animation'
import Loader from '../components/loader.component'
import { getDay } from '../common/date'
import BlogInteraction from '../components/blog-interaction.component'
import BlogPostCard from '../components/blog-post.component'
import BlogContent from '../components/blog-content.component'
import CommentContainer, { fetchComment } from '../components/comments.component'
import MDEditor from "@uiw/react-md-editor";
import {getBlogById, getCommentsByBlog} from "../coreApi/CoreApi.js";
import {Comments} from "./Comments.jsx";
import {toast} from "react-hot-toast";

export const blogStructure = {
    title: '',
    des: '',
    content: '',
    author: {
    },
    banner: '',
    publishedAt: '',
    lastUpdated:''
}

export const BlogContext = createContext({})
const BlogPage = () => {
    let { blog_id} = useParams()
    const [blog, setBlog] = useState(blogStructure);
    const [similarBlog, setSimilarBlog] = useState(blogStructure)
    const [loading, setLoading] = useState(true)
    const [isLikedByUser, setLikedByUser] = useState(false);
    const [commentsWrapper, setCommentsWrapper] = useState(false);
    const [comments, setComments] = useState({});
    const [totalParentComentsLoaded, setTotalCommentsLoaded] = useState(0)

    let {title, content, banner, author: {username , name, profile_img}, created} = blog

    const fetchBlog = async() => {
        try{

            const fetchedBlog = await getBlogById({blog_id, draft: false, mode: 'view'});
            console.log(fetchedBlog)
            setBlog(fetchedBlog)
            setSimilarBlog(fetchedBlog)
        }
        catch(err){
            console.log(err)
        }
        setLoading(false)
        // axios.post(import.meta.env.VITE_SERVER_DOMAIN + '/get-blog', {blog_id})
        // .then( async({data: {blog}})=>{
        //     blog.comments = await fetchComment({blog_id: blog._id, setParentCommentCountFun: setTotalCommentsLoaded})
        //
        //     setBlog(blog)
        //
        //     axios.post(import.meta.env.VITE_SERVER_DOMAIN + '/search-blogs', {tag: blog.tags[0], limit: 6, eliminate_blog: blog_id})
        //     .then(({data: {blogs}}) =>{
        //         setSimilarBlog(blogs)
        //     })
        //     .catch(err =>{
        //         console.log(err)
        //     })
        //     setLoading(false)
        // })

    }
    const fetchComments = async () => {
        try{

            const comments = await getCommentsByBlog({blogId:blog_id})
            const commentsModified = {
                id: 'root',
                replies:comments
            }
            setTimeout(() => {
            }, 100000);
            console.log(commentsModified)
            setComments(commentsModified);
        }
        catch (err){
            toast.error('Error fetching comments')
            console.log(err)
        }
    }


    useEffect(() =>{
        resetState()
        fetchBlog()
        fetchComments()
    }, [blog_id])
    const resetState = () =>{
        setBlog(blogStructure)
        setSimilarBlog(null)
        setLoading(true)
        setLikedByUser(false)
        setCommentsWrapper(false)
        setTotalCommentsLoaded(0)
    }
   return (
    <AnimationWrapper>
        {
            loading ? <Loader/> :
            <BlogContext.Provider value={{blog, setBlog, isLikedByUser, setLikedByUser, commentsWrapper, setCommentsWrapper, totalParentComentsLoaded, setTotalCommentsLoaded}}>
                {/*<CommentContainer/>*/}
                <div className='max-w-[900px] center py-10 max-lg:px-[5vw]'>
                    <img src={banner} alt="Banner" className='aspect-video'/>
                    <div className='mt-12'>
                        <h2>{title}</h2>
                        <div className='flex max-sm:flex-col justify-between my-8'>
                            <div className='flex gap-5 items-start '>
                                <img src={profile_img} alt="Author" className='w-12 h-12 rounded-full' />
                                <p className='capitalize '>
                                    {name}
                                    <br />
                                    @
                                    <Link  className ="underline" to={`/user/${username}`}>{username}</Link>
                                </p>

                            </div>
                            <p className='text-dark-grey opacity-75 max-sm:mt-6 mx-sm:ml-12 max-sm:pl-5'>Published on {getDay(created)}</p>
                        </div>
                    </div>

                    <BlogInteraction/>

                    <div className='my-12 font-gelasio blog-page-content'>
                        {
                            <MDEditor.Markdown source={content} style={{ whiteSpace: 'pre-wrap' }} />
                            // content[0].blocks.map((block, i) =>{
                            //     return <div className='my-4 md:my-8' key={i}>
                            //         <BlogContent block = {block}/>
                            //     </div>
                            // })
                        }

                    </div>

                    <BlogInteraction/>

                    {commentsWrapper?
                     (Object.keys(comments).includes('id'))  ?
                        <Comments blogId={blog_id} comments={comments} />
                    : <><Loader/> </> :<></>}

                    {
                        similarBlog !== null  && similarBlog.length ? 
                        <>
                        <h1 className='font-medium text-2xl mt-14 mb-10'>Similar Blogs</h1>
                        {
                            similarBlog && similarBlog.map((blog, i) =>{
                                console.log(blog)
                                let {author } = blog;
                                return <AnimationWrapper key={i} transition={{duration: 1, delay: i * 0.08}}>
                                    <BlogPostCard content={blog} author={author}/>
                                </AnimationWrapper>
                            })
                        }
                        </>
                        : ""
                    }

                </div>
            </BlogContext.Provider>
        }
    </AnimationWrapper>
  )
}

export default BlogPage
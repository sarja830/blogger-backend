import React, { useContext, useEffect } from 'react'
import { BlogContext } from '../pages/blog.page'
import { Link } from 'react-router-dom';
import { UserContext } from '../App';
import {Toaster, toast} from 'react-hot-toast'
import axios from 'axios';
import {castVote, deleteVote, getVoteByUser} from "../coreApi/CoreApi.js";

const BlogInteraction = () => {
    let blogContextData = useContext(BlogContext);
    // console.log(blogContextData)
    let {blog, blog: { id, title, voteCount, viewCount, commentCount, author:  {username: author_username}}, setBlog, isLikedByUser, setLikedByUser,  setCommentsWrapper} = blogContextData
    let {userAuth: {username, authenticationToken}} = useContext(UserContext)

    useEffect(() =>{
        const fetchLikeData = async()=>{
            try{
                const result = await getVoteByUser({blogId: id, authenticationToken})
                console.log(result);
                if(result && result.voteType === "UPVOTE"){
                    setLikedByUser(true)
                }
            }
            catch (err){
                console.log(err)
            }
        }
        console.log(authenticationToken)
        if(authenticationToken)
            fetchLikeData();
    }, [])
    const handleLike = async () =>{
        if(authenticationToken){
            setLikedByUser(preVal => !preVal)
            try{
                if (!isLikedByUser) {
                    //upvote is 1
                    //downvote is 2
                   const res =  await castVote({blogId: id, voteType: 1, authenticationToken})
                    console.log(res);
                    voteCount++;
                } else {
                    await deleteVote({blogId: id, authenticationToken})
                    voteCount--;
                }
                setBlog({...blog, voteCount})
            }
            catch(err){
                console.log(err);
                setLikedByUser(preVal => !preVal)
                if(err.response && err.response.data && err.response.data.message)
                toast.error(err.response.data.message)
            }
        }
        else{
            toast.error("Please login to like this blog")
        }
    }
    return (
        <>
            <Toaster/>
            <hr className='border-grey my-2'/>
            <div className='flex gap-6 justify-between'>
                <div className='flex gap-3 items-center'>
                    <button onClick={handleLike} className={'w-10 h-10 rounded-full flex items-center justify-center ' + (isLikedByUser ? "bg-red/20 text-red" : "bg-grey/80") }>
                        <i className={'fi ' + (isLikedByUser ? 'fi-sr-heart': 'fi-rr-heart')}></i>
                    </button>
                    <p className='text-xl text-dark-grey'>{voteCount}</p>
                    <button onClick={() => setCommentsWrapper(preVal => !preVal)} className='w-10 h-10 rounded-full flex items-center justify-center bg-grey/80'>
                        <i className='fi fi-rr-comment-dots'></i>
                    </button>
                    <p className='text-xl text-dark-grey'>{commentCount}</p>
                    <button onClick={() => setCommentsWrapper(preVal => !preVal)} className='w-10 h-10 rounded-full flex items-center justify-center bg-grey/80'>
                        <i className='fi ffi fi-rr-eye'></i>
                    </button>
                    <p className='text-xl text-dark-grey'>{viewCount}</p>
                </div>

                <div className='flex gap-6 items-center'>
                    {
                        username === author_username ?
                            <Link to={`/editor/${id}`} className='underline hover:text-purple'>Edit</Link>: ""
                    }
                    <Link to={`https://twitter.com/intent/tweet?text=Read ${title}&url=${location.href}`}><i className="fi fi-brands-twitter text-xl hover:text-twitter"></i></Link>
                </div>
            </div>

            <hr className='border-grey my-2'/>
        </>
    )
}

export default BlogInteraction
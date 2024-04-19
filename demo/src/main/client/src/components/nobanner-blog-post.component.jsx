import React from 'react'
import { Link } from 'react-router-dom'
import { getDay } from '../common/date'

const MinimulBlogPost = ({blog, index}) => {
    // let {title, id, author: {personal_info: {name, username, profile_img}}, publishedAt} = blog

    let {title, id, author: {name, username, profile_img}, created,viewCount,voteCount,category:{name:category_name}, lastUpdated} = blog
    return (
        <Link to={`/blog/${id}`} className='flex gap-5 mb-8  border-b border-grey  pb-5 mb-4'>
            <h1 className='blog-index'>{index < 10 ? "0" + ( index+1) : (index+1)}</h1>




            <div>
                <div className='flex gap-2 items-center mb-7'>
                    <img src={profile_img} alt={name} className='w-6 h-6 rounded-full' />
                    <p className='line-clamp-1'>{name} @{username}</p>
                    {/*<p className='min-w-fit'>{getDay(created)}</p>*/}
                    <p className='min-w-fit'>{getDay(lastUpdated)}</p>
                </div>
                <h1 className='blog-title'>{title}</h1>
                <div className='flex gap-4 mt-7'>
                    <span className='btn-light py-1 px-4'>{category_name}</span>
                    <span className='ml-3 flex items-center gap-2 text-dark-grey'>
                        <i className='fi fi-rr-heart text-xl'></i>{voteCount}
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 h-6 stroke-cyan-500 md:stroke-cyan-700">
                                            <path strokeLinecap="round" strokeLinejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" />
                                            <path strokeLinecap="round" strokeLinejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                                    </svg>{viewCount}
                    </span>
                </div>
            </div>
        </Link>
    )
}

export default MinimulBlogPost
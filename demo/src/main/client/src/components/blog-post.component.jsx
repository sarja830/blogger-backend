import React from 'react'
import { getDay } from '../common/date'
import { Link } from 'react-router-dom'

const BlogPostCard = ({content, author}) => {
    // all the things are coming from postgres
    //tags are not present we need to remove it and add categorises instead
    console.log(content);
    let {created,lastUpdated, tags, title, des, banner, viewCount ,voteCount,  id} = content
    let {name, profile_img, username,email} = author
    return (

        <Link to={`/blog/${id}`} className='flex gap-8 items-center border-b border-sky-400  pb-5 mb-4'>


            <div className='w-full'>




                <div className='flex gap-2 items-center mb-7'>

                    <img src={profile_img} alt={name} className='w-6 h-6 rounded-full' />
                    <p className='line-clamp-1'>{name}  &nbsp;&nbsp; |   &nbsp;&nbsp; @{username}    </p>
                    <p className='min-w-fit'>{getDay(created)}</p>


                </div>
                <h1 className='blog-title'>{title}</h1>
                <p className='my-3 text-xl font-gelasio leading-7 max-sm:hidden md:max-[1100px]:hidden line-clamp-2'>{des}</p>
                <div className='flex gap-4 mt-7'>

                    <span className='ml-3 flex items-center gap-2 text-dark-grey'>
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 h-6 stroke-red-300 md:stroke-red-400">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M21 8.25c0-2.485-2.099-4.5-4.688-4.5-1.935 0-3.597 1.126-4.312 2.733-.715-1.607-2.377-2.733-4.313-2.733C5.1 3.75 3 5.765 3 8.25c0 7.22 9 12 9 12s9-4.78 9-12Z" />
                                </svg>{voteCount}
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor"  className="w-6 h-6 stroke-cyan-500 md:stroke-cyan-700">
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" />
                                    <path strokeLinecap="round" strokeLinejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                                </svg>{viewCount}
                    </span>
                    <span className='btn-light py-1 px-4'>{tags[0]}</span>
                </div>
            </div>
            <div className='h-28 aspect-square bg-grey'>
                <img src={banner} alt="Banner" className='w-full h-full aspect-square object-cover'/>

            </div>
        </Link>

    )
}

export default BlogPostCard
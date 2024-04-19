import React, {useContext, useEffect, useState} from 'react'
import {UserContext} from '../App'
import {filterPaginationData} from '../common/filter-pagination-data'
import {Toaster} from 'react-hot-toast'
import InPageNavigaion from '../components/inpage-navigation.component'
import Loader from '../components/loader.component'
import NoDataMessage from '../components/nodata.component'
import AnimationWrapper from '../common/page-animation'
import {ManageDraftBlogPost, ManagePublishedBlogCard} from '../components/manage-blogcard.component'
import LoadMoreDataBtn from '../components/load-more.component'
import {useNavigate, useSearchParams} from 'react-router-dom'
import {getBlogByAuthor} from "../coreApi/CoreApi.js";
import {HandleError} from "../common/HandleError.jsx";

const ManageBlog = () => {
    const [blogs, setBlogs] = useState(null)
    const [drafts, setDrafts] = useState(null)
    const [query, setQuery] = useState('')
    let navigate = useNavigate();
    let activeTab = useSearchParams()[0].get('tab')


    let {userAuth: {authenticationToken} }= useContext(UserContext)

    const getBlog = async ({page, draft, deletedDocCount = 0}) => {
        try {
            console.log(authenticationToken);
            const fetchedBlogs = await getBlogByAuthor({page, draft, deletedDocCount, query, authenticationToken})
            console.log(fetchedBlogs);
            let formattedData = await filterPaginationData({
                state: draft ? drafts : blogs,
                data: fetchedBlogs,
                page,
                user: authenticationToken,
                countRoute: '/blogs/count/author',
                data_to_send: {draft, query}
            })

            console.log(formattedData);
            if (draft) {
                setDrafts(formattedData)
            } else {
                setBlogs(formattedData)
            }
        }
        catch(err){
            HandleError(err,navigate);
            console.log(err)
        }
    }

    const handleChange = (e) => {
        if(e.target.value.length){
            setQuery("");
            setBlogs(null)
            setDrafts(null)

        }
    }
    const handleSearch = (e) => {
        let searchQuery = e.target.value;
        setQuery(searchQuery)

        if(e.keyCode == 13 && searchQuery.length){
            setBlogs(null)
            setDrafts(null)
        }
    }

    useEffect(() => {
        if(authenticationToken){
            if(blogs == null){
                getBlog({page: 0, draft: false})
            }
            if(drafts == null){
                getBlog({page: 0, draft: true})
            }
        }
    }, [authenticationToken, blogs, drafts, query])

    return (
        <>
            <h1 className='max-md:hidden text-2xl'>Manage Blog</h1>
            <Toaster/>
            <div className='relative max-md:mt-5 md:mt-8 mb-10'>
                <input
                    type="search"
                    className='w-full bg-grey p-4 pl-12 pr-6 rounded-full placeholder:text-dark-grey'
                    placeholder='Search Blogs'
                    onChange={handleChange}
                    onKeyDown={handleSearch}
                />
                <i className='fi fi-rr-search absolute right-[10%] md:pointer-events-none md:left-5 top-1/2 -translate-y-1/2 text-xl text-dark-grey'></i>
            </div>

            <InPageNavigaion routes={["Published Blogs", "Drafts Blogs"]} defaultActiveIndex={activeTab !== 'draft' ? 0 : 1}>
                {
                    blogs == null ? <Loader/> :
                        blogs.results.length ?
                            <>
                                {
                                    blogs.results.map((blog, i) => {
                                        return <AnimationWrapper key={i} transition={{delay: i*0.04}}>
                                            <ManagePublishedBlogCard blog={{...blog, index: i, setStateFunc: setBlogs}}/>
                                        </AnimationWrapper>
                                    })
                                }
                                <LoadMoreDataBtn state={blogs} fetchDataFunction={getBlog} additionalParam={{draft: false, deletedDocCount: blogs.deletedDocCount}}/>
                            </>
                            : <NoDataMessage message='No Published Blogs Available'/>
                }
                {
                    drafts == null ? <Loader/> :
                        drafts.results.length ?
                            <>
                                {
                                    drafts.results.map((blog, i) => {
                                        return <AnimationWrapper key={i} transition={{delay: i*0.04}}>
                                            <ManageDraftBlogPost blog={{...blog, index: i, setStateFunc: setDrafts}}/>
                                        </AnimationWrapper>
                                    })
                                }
                                <LoadMoreDataBtn state={drafts} fetchDataFunction={getBlog} additionalParam={{draft: true, deletedDocCount: drafts.deletedDocCount}}/>
                            </>
                            : <NoDataMessage message='No Draft Blogs Available'/>
                }
            </InPageNavigaion>
        </>
    )
}

export default ManageBlog
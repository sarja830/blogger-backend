import React, { useEffect, useState } from "react";
import AnimationWrapper from "../common/page-animation";
import InPageNavigaion, { activeTabRef } from "../components/inpage-navigation.component";
import axios from "axios";
import Loader from "../components/loader.component";
import BlogPostCard from "../components/blog-post.component";
import MinimulBlogPost from "../components/nobanner-blog-post.component";
import NoDataMessage from "../components/nodata.component";
import { filterPaginationData } from "../common/filter-pagination-data";
import LoadMoreDataBtn from "../components/load-more.component";
import {getBlogByCategory, getCategories, getLatestBlog, getTrendingBlog} from "../coreApi/CoreApi.js";
import {toast} from "react-hot-toast";

const Home = () => {
    let [blogs, setBlogs] = useState(null);
    let [trendingBlogs, setTrendingBlogs] = useState(null);
    let [pageState, setPageState] = useState('home')
    let [selectedCategory, setSelectedCategory] = useState([])
    let [categories, setCategories] = useState([]);


    useEffect(() => {
        const fetchData = async () => {
            try{
                const categories =await getCategories();
                setCategories(categories);
            }
            catch(error){
                console.log(error);
                toast.error("Unable to fetch category list");
            }
        }
        fetchData();
    },[] )

    useEffect(() => {
        activeTabRef.current.click()
        if(pageState === 'home')
            fetchLatestBlogs({page: 0});
        else{
            fetchBlogByCategory({page: 0})
        }
        if(!trendingBlogs)
            fetchTrendingBlogs();
    }, [pageState]);
    const fetchTrendingBlogs =async () => {
        try {
            const fetchedBlogs = await  getTrendingBlog({});
            setTrendingBlogs(fetchedBlogs);
        }
        catch(err) {
            console.log(err);
        }
    };
    const fetchLatestBlogs = async ({params, page = 0}) => {
        try{
            console.log("page")
            console.log(page)

            const fetchedBlogs = await  getLatestBlog({page})
            // console.log(data.blogs)
            let formatedData = await filterPaginationData({
                state: blogs,
                data:fetchedBlogs,
                page,
                countRoute: '/blogs/count'
            });
            console.log(formatedData)
            setBlogs(formatedData)
        }
        catch (e) {
            console.log(e);
        }
    };

    const fetchBlogByCategory = async({page = 0}) => {

        try{
            const fetchedBlogs = await getBlogByCategory({tag: pageState,category_ids:selectedCategory, page});
            let formatedData = await filterPaginationData({
                state: blogs,
                data: fetchedBlogs,
                page,
                countRoute: '/blogs/count',
                countParams:{ category_ids: selectedCategory },
                data_to_send: {tag: pageState}
            })
            console.log(formatedData)
            setBlogs(formatedData)
        }
        catch(err) {
            console.log(err);
        };
    };
    const loadBlogbyCategory = (e) =>{

        let category_name = e.target.innerText.toLowerCase();
        let category_id = e.target.value;
        setBlogs(null)
        if(pageState === category_name){
            setPageState('home');
            setSelectedCategory([])
            return;
        }
        setPageState(category_name)
        setSelectedCategory([category_id]);

    }

    return (

        <AnimationWrapper>
            <section className="h-cover flex justify-center gap-10">
                {/* latest blog */}
                <div className="w-full">
                    <InPageNavigaion
                        routes={[pageState, "trending blog"]}
                        defaultHidden={["trending blog"]}
                    >
                        <>
                            {blogs === null ? (
                                <Loader />
                            ) : (
                                !blogs.results.length ? <NoDataMessage message={'No blog published'}/> :
                                    blogs.results.map((blog, i) => {
                                        return (
                                            <AnimationWrapper
                                                key={i}
                                                transition={{ duration: 1, delay: i * 0.1 }}
                                            >
                                                <BlogPostCard
                                                    content={blog}
                                                    author={blog.author}
                                                />
                                            </AnimationWrapper>
                                        );
                                    })
                            )}
                            <LoadMoreDataBtn state={blogs} fetchDataFunction={(pageState === 'home' ? fetchLatestBlogs : fetchBlogByCategory )}/>
                        </>
                        {trendingBlogs === null ? (
                            <Loader />
                        ) : (
                            trendingBlogs.length ?
                                trendingBlogs.map((blog, i) => {
                                    return (
                                        <AnimationWrapper
                                            key={i}
                                            transition={{ duration: 1, delay: i * 0.1 }}
                                        >
                                            <MinimulBlogPost blog={blog} index={i} />
                                        </AnimationWrapper>
                                    )
                                })
                                : <NoDataMessage message={'No blog Trending'}/>
                        )}
                    </InPageNavigaion>
                </div>
                {/* filter and trending */}
                <div className="min-w-[40%] lg:min-w-[400px] max-w-min border-l border-grey pl-8 pt-3 max-md:hidden">
                    <div className="flex flex-col gap-10">
                        <div>
                            <h1 className="font-medium text-xl mb-8">
                               Choose from Categories <i className="fi fi-rr-list"></i>
                            </h1>
                            <div className="flex gap-3 flex-wrap">
                                {categories.map((category, i) => {
                                    return (
                                        <button onClick={loadBlogbyCategory} value={category.id} key={category.id} className={"tag " + (pageState === category.name.toLowerCase() ? "bg-black text-white" : "") }>
                                            {category.name}
                                        </button>
                                    );
                                })}
                            </div>
                        </div>

                        <div>
                            <h1 className="text-xl font-medium mb-8">
                                Trending <i className="fi fi-rr-arrow-trend-up"></i>
                            </h1>
                            {trendingBlogs === null ? (
                                <Loader />
                            ) : (
                                trendingBlogs.length ?
                                    trendingBlogs.map((blog, i) => {
                                        return (
                                            <AnimationWrapper
                                                key={i}
                                                transition={{ duration: 1, delay: i * 0.1 }}
                                            >
                                                <MinimulBlogPost blog={blog} index={i} />
                                            </AnimationWrapper>
                                        );
                                    })
                                    : <NoDataMessage message={'No blog Trending'}/>
                            )}
                        </div>
                    </div>
                </div>
            </section>
        </AnimationWrapper>
    );
};

export default Home;

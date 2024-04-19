import axios from "axios";
import {getBlogCount, getBlogCountByAuthor} from "../coreApi/CoreApi.js";

export const filterPaginationData = async ({create_new_arr = false, state, data, page, countRoute, data_to_send={},countParams, user = undefined}) => {
    let obj;
    let category_ids = []
    if(countParams)
        if(Object.keys(countParams).includes('category_ids'))
            category_ids = countParams.category_ids;

    if (state !== null && !create_new_arr) {
        obj = {...state, results: [...state.results, ...data], page: page}
    } else {
        obj = {results: data, page: 0, totalDocs:0 }
        try {
            let totalBlogs;

            if(countRoute==='/blogs/count')
                totalBlogs = await getBlogCount({data_to_send,category_ids})
            else
                totalBlogs = await getBlogCountByAuthor({...data_to_send,category_ids,authenticationToken:user});
            console.log(totalBlogs)
            obj = {results: data, page: 0, totalDocs:totalBlogs}
        } catch (err) {
            console.log(err)
        }
    }
    return obj
}
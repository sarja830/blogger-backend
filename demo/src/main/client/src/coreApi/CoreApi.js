
import axios from "axios";


const api = axios.create({
    baseURL: import.meta.env.VITE_SERVER_DOMAIN
});

export const loginSignup = async ({serverRoute, formData}) => {
    const response = await api.post( serverRoute,formData);
    return response.data.data;
};
export const getBlogById = async ({  blog_id, draft, mode}) => {
    const response = await api.get(  `/blogs/blogId/${blog_id}`, {
            draft: draft,
            mode: mode || 'view' });
    return response.data.data;
};
export const getBlogByIdByAuthor = async ({  blog_id, draft, mode, authenticationToken}) => {
    console.log(authenticationToken);
    let params = new URLSearchParams();
    params.append("draft",draft);
    params.append("mode",mode|| 'view');

    const response = await api.get(`/blogs/author/blogId/${blog_id}`, { params,
            headers: {
                'Authorization': `Bearer ${authenticationToken}`
            }});
    return response.data.data;
};
export const getBlogByAuthor = async ({  category_ids, draft, authenticationToken, page,page_size}) => {
    console.log(authenticationToken)
    let params = new URLSearchParams();
    if(category_ids)
        for (const category_id of category_ids)
            params.append("category_id",category_id);
    params.append("page",page);
    params.append("draft",draft);
    params.append("page_size",5||page_size);
    const response = await api.get(  '/blogs/author', { params ,
        headers: {
            'Authorization': `Bearer ${authenticationToken}`
        }
    });
    console.log(response);
    return response.data.data;
};
export const getBlogCount = async ({  category_ids , author_ids,headers}) => {
    console.log(category_ids);
    let params = new URLSearchParams();
    if(category_ids)
        for (const category_id of category_ids)
            params.append("category_id",category_id);
    if(author_ids)
        for (const author_id of author_ids)
            params.append("author_id",author_id);

    const response = await api.get(  '/blogs/count', { params }, headers);
    console.log(response.data.data)
    return response.data.data;
};
export const getVoteByUser = async ({  blogId,authenticationToken}) => {
    if(!blogId)
        return Error("Blog Id is required");
    let params = new URLSearchParams();
    params.append("blogId",blogId);

    const response = await api.get(  `/votes`, { params ,
        headers: {
            'Authorization': `Bearer ${authenticationToken}`
        }
    });
    console.log(response.data.data)
    return response.data.data;
};
export const getCommentsByBlog = async ({  blogId}) => {
    if(!blogId)
        return Error("Blog Id is required");
    let params = new URLSearchParams();
    params.append("blogId",blogId);

    const response = await api.get(  `/comments`, { params});
    console.log(response.data.data)
    return response.data.data;
};
export const postComment = async ({  blogId,comment,parentId, authenticationToken}) => {

    if(!blogId)
        return Error("Blog Id is required");
    if(comment && comment === "")
        return Error("Please write something to comment");

    if(parentId==='root')
        parentId = null;

    const response = await api.post(  `/comments`,
        { blogId,comment,parentId},
        {headers: { 'Authorization': `Bearer ${authenticationToken}`}});
    console.log(response.data.data)
    return response.data.data;
};
export const editComment = async ({  id, comment, authenticationToken}) => {

    if(comment && comment === "")
        return Error("Please write something to comment");

    const response = await api.put(  `/comments`,
        { id,comment},
        {headers: { 'Authorization': `Bearer ${authenticationToken}`}});
    console.log(response.data.data)
    return response.data.data;
};
export const deleteComment = async ({  id, authenticationToken}) => {
    if(!id)
        return Error("Comment Id is required");
    var params = new URLSearchParams();
    params.append("id",id);
    const response = await api.delete(  `/comments`, {params,headers: { 'Authorization': `Bearer ${authenticationToken}`}});
    console.log(response.data.data)
    return response.data.data;
};
export const castVote = async ({  blogId,voteType, authenticationToken}) => {
    if(!blogId)
        return Error("Blog Id is required");
    if(!voteType)
        return Error("Vote Type is required");

    const response = await api.post(  `/votes`,
        { blogId,voteType },
        {headers: { 'Authorization': `Bearer ${authenticationToken}`}});
    console.log(response.data.data)
    return response.data.data;
};
export const deleteVote = async ({  blogId, authenticationToken}) => {
    if(!blogId)
        return Error("Blog Id is required");
   console.log(blogId)
    let params = new URLSearchParams();
    params.append("blogId",blogId);

    const response = await api.delete(  `/votes`,
        { params , headers: { 'Authorization': `Bearer ${authenticationToken}`}});
    console.log(response.data.data)
    return response.data.data;
};
export const sendResetPasswordMail = async ({  email}) => {
    if(!email)
        return Error("Email is required");
    const response = await api.post(  `/auth/passwordResetRequest`, { email });
    console.log(response.data.data)
    return response.data.data;
};
export const resendVerificationMail = async ({email}) => {
    if(!email)
        return Error("Email is required");
    const response = await api.get(  `/auth/resend-verification-token?email=${email}`);
    console.log(response.data.data)
    return response.data.data;
};
export const getBlogCountByAuthor = async ({  draft, category_ids ,authenticationToken}) => {

    console.log(draft);
    let params = new URLSearchParams();
    if(category_ids)
        for (const category_id of category_ids)
            params.append("category_id",category_id);
    params.append("draft",draft);

    const response = await api.get(  '/blogs/author/count', { params ,
        headers: {
            'Authorization': `Bearer ${authenticationToken}`
        }});
    console.log(response.data.data)
    return response.data.data;
};
export const getTrendingBlog = async ({  page}) => {
    const response = await api.get(  '/blogs', {params: {page, page_size: 5, filter: "viewCount", value:"desc"}} );
    return response.data.data;
};
export const getLatestBlog = async ({ page }) => {
    const response = await api.get(  '/blogs',{params: { page,page_size:5, filter: "created", value: "desc"}} );
    return response.data.data;
};
export const getBlogByCategory = async ({  category_ids ,author_ids, page}) => {
    let params = new URLSearchParams();
    if(category_ids)
        for (const category_id of category_ids)
            params.append("category_id",category_id);
    params.append("page",page);
    params.append("page_size",5);
    const response = await api.get(  '/blogs', { params });

    return response.data.data;
};
export const createBlog = async ({ blogObj,blog_id,authenticationToken}) => {
    let response;
    if(blog_id) {
        response = await api.put('/blogs', {...blogObj,id:blog_id}, {
            headers: {
                'Authorization': `Bearer ${authenticationToken}`
            }
        });
    }
    else {
        response = await api.post('/blogs', {...blogObj}, {
            headers: {
                'Authorization': `Bearer ${authenticationToken}`
            }
        });
    }
    return response;
};
export const changePassword = async ({formData},username) => {
    console.log(formData);
    console.log(username)
    const response = await api.post('/auth/changePassword', {...formData,username});
    return response;
};
export const verifyAccount = async ({token}) => {
    // http://localhost:8080/api/auth/accountVerification/efdc6b6d-a60f-450d-bab1-355fc82c94d5
    const response = await api.get(`/auth/accountVerification/${token}`);
    return response;
};
export const resetPassword = async ({formData}) => {
    console.log(formData);
    const response = await api.post('/auth/resetPassword', {...formData});
    return response;
};


export const getCategories = async () => {
    const response = await api.get(`/categories`, {});
    return response.data.data;
};
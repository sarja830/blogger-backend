import React, {useContext, useEffect, useState} from 'react';

import { createContext } from 'react';
import {
    addNode,
    deleteNode,
    editNode,
    addCommentsInLocalStorage,
} from "../utils/utils.js";
import ReplyForm from "../components/replyForm.jsx";
import ReplyCard from "../components/replyCard.jsx";

import {toast} from "react-hot-toast";
import {UserContext} from "../App.jsx";
import {deleteComment, editComment, postComment} from "../coreApi/CoreApi.js";
import {BlogContext} from "./blog.page.jsx";


const ReplyContext = createContext(null);

export const useReplyContext = () => useContext(ReplyContext);

export const Comments = ({blogId,comments}) => {
    console.log(comments);
    // step 1 entrypoint
    const [allComments, setAllComments] = useState(comments);
    let blogContextData = useContext(BlogContext);
    let {blog, blog: { id, title, voteCount, viewCount, commentCount, author:  {username: author_username}}, setBlog, isLikedByUser, setLikedByUser,  setCommentsWrapper} = blogContextData;

    let {userAuth: {authenticationToken,username}} = useContext(UserContext);
    const [activeEditReplyId, setActiveEditReplyId] = useState('');




    const cancelActiveEditReplyId = () => setActiveEditReplyId('');

    const updateActiveEditReplyId = (replyId) => setActiveEditReplyId(replyId);

    const addReply = async(replyDataToAdd, parentId) => {
        console.log("in add reply")
        let loadingToast =  toast.loading("Please wait...");
        try{
            const allCommentsClone = structuredClone(allComments);
            const id = await postComment({ blogId, comment: replyDataToAdd.comment, authenticationToken , parentId})
            replyDataToAdd.id = id;
            addNode(allCommentsClone, replyDataToAdd, parentId);
            setAllComments(allCommentsClone);
            setBlog({...blog, commentCount: blog.commentCount + 1});
            toast.success('success');
        }
        catch(err){
            console.log(err);
            toast.error("There is something wrong with the server");

        }
        finally{
            toast.dismiss(loadingToast);
        }

        // addCommentsInLocalStorage(allCommentsClone);
    };

    const editReply = async (edittedText, replyIdForEdit) => {
        let loadingToast =  toast.loading("Please wait...");
        try {
            const allCommentsClone = structuredClone(allComments);
            // method to edit the comment structure
            console.log(replyIdForEdit);
            await editComment({ id:replyIdForEdit, comment: edittedText, authenticationToken});
            editNode(allCommentsClone, edittedText, replyIdForEdit);
            setAllComments(allCommentsClone);
            cancelActiveEditReplyId();
            toast.success('Reply edited successfully');

            // addCommentsInLocalStorage(allCommentsClone);
        } catch (err) {
            console.log(err);
            toast.error('Something went wrong!! Please try again Later.');
        }
        finally{
            toast.dismiss(loadingToast);
        }
    };

    const deleteReply = async (replyId,username) => {
        let loadingToast =  toast.loading("Please wait...");
        try{
            const allCommentsClone = structuredClone(allComments);
            await deleteComment({id: replyId, authenticationToken});
            deleteNode(allCommentsClone, replyId, username);
            setAllComments(allCommentsClone);
            addCommentsInLocalStorage(allCommentsClone);
            toast.success('comment deleted successfully');
        }
        catch(err){
            console.log(err);
            toast.error('Something went wrong!! Please try again Later.');
        }
        finally{
            toast.dismiss(loadingToast);
        }
    };

    return (
        <ReplyContext.Provider
            value={{
                allComments,
                addReply,
                editReply,
                deleteReply,
                cancelActiveEditReplyId,
                activeEditReplyId,
                updateActiveEditReplyId,
            }}
        >
            <main className='p-5'>
                <ReplyForm
                    isEditingAndData={null}
                    isAddingAndParentId={allComments.id}
                    closeForm={null}
                />

                <div className='m-2 mt-4 ml-0'>
                    {allComments.replies.map((reply) => (
                        <ReplyCard replyData={reply} key={reply.id} />
                    ))}
                </div>
            </main>
        </ReplyContext.Provider>
    );
}

export default Comments;

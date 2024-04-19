import React, { createContext, useContext, useEffect, useState } from 'react'
import { UserContext } from '../App'
import {Navigate, useNavigate, useParams} from 'react-router-dom'
import BlogEditor from '../components/blog-editor.component';
import PublishForm from '../components/publish-form.component';
import Loader from '../components/loader.component';
import { getBlogByIdByAuthor} from "../coreApi/CoreApi.js";
import {HandleError} from "../common/HandleError.jsx";

const blogStructure = {
    title: '',
    banner: '',
    content: '',
    tags: [],
    des: '',
    author: { personal_info: { } }
}


export const EditorContext = createContext({})
const Editor = () => {

    let {blog_id} = useParams()
    const [blog ,setBlog] = useState(blogStructure)
    const [editorState, setEditorState] = useState("editor");
    const [textEditor, setTextEditor] = useState({isReady: false})
    let {userAuth: {authenticationToken}} = useContext(UserContext)
    const [loading, setLoading] = useState(true)
    let navigate = useNavigate();

    useEffect(() => {
            if (!blog_id)
                return setLoading(false);
            const fetchBlogToUpdate = async () => {
                try {
                    console.log(authenticationToken)
                    const blog = await getBlogByIdByAuthor({
                        blog_id,
                        draft: true,
                        mode: 'edit',
                        authenticationToken
                    });
                    console.log(blog)
                    setBlog(blog)
                    setLoading(false)
                } catch (err) {
                    console.log(err);
                    setLoading(false)
                    HandleError(err,navigate);
                }
            }
            fetchBlogToUpdate();
        }
        , [])
    return (
        <EditorContext.Provider value={{blog, setBlog, editorState, setEditorState, textEditor, setTextEditor}}>
            {
                authenticationToken === null || undefined ? <Navigate to= '/signin' /> :
                    loading ? <Loader/> :
                        editorState === "editor" ? <BlogEditor/> : <PublishForm/>
            }
        </EditorContext.Provider>

    )
}

export default Editor
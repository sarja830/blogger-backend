import {mainUser} from "../components/user.js";
import {useContext} from "react";
import {UserContext} from "../App.jsx";

// let {userAuth: {authenticationToken,username}} = useContext(UserContext);

const addNode = (obj, data, parentId) => {

    console.log(data)
    console.log(obj)
    console.log(parentId)
    if (obj.id === parentId) {
        obj.replies.push(data);
        return;
    }

    obj.replies.forEach((childObj) => {
        addNode(childObj, data, parentId);
    });
};

const editNode = (obj, edittedText, idWhoseTextToEdit) => {
    console.log(obj);
    console.log(idWhoseTextToEdit)
    console.log(edittedText)
    if (obj.id === idWhoseTextToEdit) {
        console.log(obj);
        obj.comment = edittedText;
        return;
    }
    console.log(obj);
    console.log('-------------------')
    obj.replies.forEach((childObj) => {
        editNode(childObj, edittedText, idWhoseTextToEdit);
    });
};

const deleteNode = (obj, replyId,username) => {
    console.log(obj)
    if (obj.id === replyId) {
        obj.isDeleted = true;
        return;
    }

    obj.replies.forEach((childObj) => {
        deleteNode(childObj, replyId);
    });

    const indexOfObjToBeRemoved = obj.replies.findIndex(
        ({ isDeleted, replies, commentor: { username : commentor_name } }) =>
            isDeleted && commentor_name === username && replies.length < 1
    );
    if (indexOfObjToBeRemoved !== -1)
        obj.replies.splice(indexOfObjToBeRemoved, 1);
};

export const addCommentsInLocalStorage = (allCommentsClone) =>
    localStorage.setItem('all-comments', JSON.stringify(allCommentsClone));

export const getCommentsFromLocalStorage = () =>
    JSON.parse(localStorage.getItem('all-comments'));
export { deleteNode, addNode, editNode };

import React, {useContext, useState} from 'react';
import { FaCaretDown, FaCaretUp, FaEdit, FaTrash } from 'react-icons/fa';
import ReplyForm from './ReplyForm';
import { useReplyContext } from '../pages/Comments';
import {mainUser} from "./user.js";
import {UserContext} from "../App.jsx";


const ReplyCard = ({ replyData }) => {
    const {
        activeEditReplyId,
        updateActiveEditReplyId,
        deleteReply,
        cancelActiveEditReplyId,
    } = useReplyContext();


    const {
        id,
        replies,
        commentor: { profileImage, username, id: userId },
        comment,
        isDeleted,
    } = replyData;

    let {userAuth: {authenticationToken,username: currentUser, }} = useContext(UserContext);
    const isReplyCurrentlyEditing = activeEditReplyId === id;

    const [isRepliesVisible, setIsRepliesVisible] = useState(true);

    const [isReplying, setIsReplying] = useState(false);

    const toggleIsReplying = () => {
        if (!isReplying && !!activeEditReplyId) {
            cancelActiveEditReplyId();
        }
        setIsReplying(!isReplying);
    };

    return (
        <article className='mb-2 ml-2'>
            <header className='flex gap-2 align-middle b'>
                <div className='rounded-full w-8 h-8 overflow-hidden'>
                    <img
                        src={profileImage || 'https://i.imgur.com/HeIi0wU.png'}
                        alt={username}
                        className='w-full h-full object-cover'
                    />
                </div>

                {isDeleted ? (
                    <span className='lowercase text-gray-400'>
            Reply Deleted by {username}
          </span>
                ) : (
                    <h4 className='lowercase font-semibold text-xl'>{username}</h4>
                )}
            </header>

            <div className={`m-1 ml-4 border-l-2 border-gray-400`}>
                <main className={`${isReplyCurrentlyEditing ? 'pl-2' : 'pl-5'} pb-4`}>
                    {isReplyCurrentlyEditing ? (
                        <ReplyForm
                            isEditingAndData={replyData}
                            isAddingAndParentId={null}
                            closeForm={cancelActiveEditReplyId}
                        />
                    ) : (
                        <>
                            {!isDeleted && <p className='mb-1'>{comment}</p>}

                            <div className='flex gap-3'>
                                {replies.length > 0 && (
                                    <button
                                        className='flex gap-1 text-blue-700 font-semibold '
                                        onClick={() => setIsRepliesVisible(!isRepliesVisible)}
                                    >
                    <span className='mt-1'>
                      {isRepliesVisible ? <FaCaretDown /> : <FaCaretUp />}
                    </span>{' '}
                                        {replies.length} repl{replies.length === 1 ? 'y' : 'ies'}
                                    </button>
                                )}

                                <button
                                    onClick={toggleIsReplying}
                                    className='font-semibold text-gray-400'
                                >
                                    Reply
                                </button>

                                {currentUser && username === currentUser && !isDeleted &&
                                    <>
                                        <button
                                            onClick={() => updateActiveEditReplyId(id)}
                                            className='font-semibold'
                                        >
                                            <FaEdit />
                                        </button>

                                        <button
                                            onClick={() => deleteReply(id)}
                                            className='font-semibold text-red-600 hover:text-red-800'
                                        >
                                            <FaTrash />
                                        </button>
                                    </>
                                }
                            </div>
                        </>
                    )}
                </main>
                {isReplying && (
                    <div className='mt-0 ml-2 mb-4 flex gap-2'>
                        <ReplyForm
                            isEditingAndData={null}
                            isAddingAndParentId={id}
                            closeForm={toggleIsReplying}
                        />
                    </div>
                )}
                {isRepliesVisible && (
                    <div>
                        {replies.map((reply) => (
                            <ReplyCard replyData={reply} key={reply.id} />
                        ))}
                    </div>
                )}


            </div>
        </article>
    );
};

export default ReplyCard;

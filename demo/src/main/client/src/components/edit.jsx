


// const uploadImg = () =>
//     "https://images.unsplash.com/photo-1689671439720-47c45b6a7a74?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw1fHx8ZW58MHx8fHx8&auto=format&fit=crop&w=500&q=60";
// }

import {uploadImage} from "../common/awsFileUpload.jsx";

const uploadImg = async(files,authenticationToken) => {
    const urls = [];
    console.log(files)
    for(const file of files) {
        try {
            let url = await uploadImage(file, authenticationToken);
            urls.push(url);
        }
        catch(err)
        {
            console.log(err);
        }
    }
    console.log(urls);
    return urls;
};


export const onImageUpload = async (files, api,authenticationToken) => {
    let urls = [];
    console.log(files)
    urls = await uploadImg(files,authenticationToken);
    if(urls.length === 0)
        return Promise.reject("There is some error in uploading the file. Please try again. ");
    let insertedMarkdown = "";
    for (const url of urls)
        insertedMarkdown +=
            `**![](${url})**` +
            `<!--rehype:style=display: flex; justify-content: center; width: 100%; max-width: 500px; margin: auto; margin-top: 4px; margin-bottom: 4px; -->`;
    if (!insertedMarkdown) return;

    api.replaceSelection(insertedMarkdown);
    return urls;
};

export const onImageUpload_DnD = async (file, setMarkdown,authenticationToken) => {
    const url = await uploadImg(file,authenticationToken);

    const insertedMarkdown =
        `**![](${url})**` +
        `<!--rehype:style=display: flex; justify-content: center; width: 100%; max-width: 500px; margin: auto; margin-top: 4px; margin-bottom: 4px; -->`;
    if (!insertedMarkdown) return;

    setMarkdown((prev) => prev + insertedMarkdown);
};

export const extractYouTubeId = (url) => {
    const pattern = /(?:https?:\/\/(?:www\.)?)?(?:youtube\.com\/(?:watch\?(?:.*&)?v=|embed\/|v\/|u\/\w\/|playlist\?list=)|youtu\.be\/)([^#]{11})/;

    const match = url.match(pattern);

    if (match && match[1]) return match[1];
    return;
};

export const onImageDrop = async (dataTransfer, setMarkdown,authenticationToken) => {
    const files = [];

    for (let index = 0; index < dataTransfer.items.length; index++) {
        const file = dataTransfer.files.item(index);
        if (file) files.push(file);
    }

    await Promise.all(
        files.map(async (file) => onImageUpload_DnD(file, setMarkdown,authenticationToken))
    );
};

export const imgBtn = (inputRef, textApiRef) => ({
    name: "Text To Image",
    keyCommand: "text2image",
    render: (command, disabled, executeCommand) => {
        return (
            <button
                type="button"
                aria-label="Insert title3"
                disabled={disabled}
                onClick={() => {
                    executeCommand(command, command.groupName);
                }}
            >
                <svg width="12" height="12" viewBox="0 0 20 20">
                    <path
                        fill="currentColor"
                        d="M15 9c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2zm4-7H1c-.55 0-1 .45-1 1v14c0 .55.45 1 1 1h18c.55 0 1-.45 1-1V3c0-.55-.45-1-1-1zm-1 13l-6-5-2 2-4-5-4 8V4h16v11z"
                    ></path>
                </svg>
            </button>
        );
    },
    execute: (state, api) => {
        inputRef.current.click();
        textApiRef.current = api;
    }
});


export const editChoice = (inputRef, textApiRef) => [
    imgBtn(inputRef, textApiRef)
];

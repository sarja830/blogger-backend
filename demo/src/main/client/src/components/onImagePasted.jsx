
// import { fileUpload } from '../../../../../libs/firebase/storage';
import insertToTextArea from './insertToTextArea';

const onImagePasted = async (dataTransfer,  setValue )=> {
    // setvalue: (value: SetStateAction)
    const files = [];
    for (let index = 0; index < dataTransfer.items.length; index += 1) {
        const file = dataTransfer.files.item(index);

        if (file) {
            files.push(file);
        }
    }
    const fileUploader = (file) => {
        console.log(file);
        const imageURL = URL.createObjectURL(file);
        console.log(imageURL    );
        return imageURL;
    };

    for (const file of files) {
        try {
            const url = await fileUploader(file); // get url
            const insertedValue = insertToTextArea(`![](${url})`);
            if (!insertedValue) {
                continue;
            }
            setValue(insertedValue);
            // setValue((prev) => {
            //     console.log(prev);
            //     return prev + insertedValue;
            // });
        } catch (error) {
            console.error('Error:', error);
        }
    }
    // await Promise.all(
    //     files.map(async (file) => {
    //         const url = await fileUploader(file);// get url
    //         const insertedvalue = insertToTextArea(`![](${url})`);
    //         if (!insertedvalue) {
    //             return;
    //         }
    //         setValue((prev)=> {
    //             console.log(prev)
    //             return prev + insertedvalue;
    //         });
    //
    //     }),
    // );
};

export default onImagePasted;
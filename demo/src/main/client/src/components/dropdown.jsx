import React, { useState } from 'react';

function Dropdown({options,callback}) {

    const [selected, setSelected] = useState(options.default);
    const listElements = options.categories.map(option => {
        const content = `${option.id} ${option.name} - ${option.parentId} `
        return <option key={option.id} value={option.id}>{content}</option>
    });

    return (
        <div className='text-dark-grey mb-2 mt-9'>
            <p className='text-dark-grey mb-2 '><b>Category *</b></p>
            <select
                className='input-box pl-4'
                value={selected}
                onChange={e => {
                    setSelected(e.target.value)
                    callback(e.target.value)
                }}
            >
                <option key={0} value={''}>{`categoryId name - parentId `}</option>
                {listElements}
            </select>
        </div>);
}

//
//
// <div className="relative">
//     <button
//         id="dropdownInformationButton"
//         onClick={() => setIsOpen(!isOpen)}
//         className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center inline-flex items-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
//         type="button"
//     >
//         Dropdown header
//         <svg
//             className={`w-2.5 h-2.5 ms-3 transform ${isOpen && 'rotate-180'}`}
//             aria-hidden="true"
//             xmlns="http://www.w3.org/2000/svg"
//             fill="none"
//             viewBox="0 0 10 6"
//         >
//             <path
//                 stroke="currentColor"
//                 strokeLinecap="round"
//                 strokeLinejoin="round"
//                 strokeWidth="2"
//                 d="m1 1 4 4 4-4"
//             />
//         </svg>
//     </button>
//     {isOpen && (
//         <div
//             id="dropdownInformation"
//             className="z-10 bg-white divide-y divide-gray-100 rounded-lg shadow w-44 dark:bg-gray-700 dark:divide-gray-600 absolute top-full mt-1"
//         >
//             <div className="px-4 py-3 text-sm text-gray-900 dark:text-white">
//                 <div>Bonnie Green</div>
//                 <div className="font-medium truncate">name@flowbite.com</div>
//             </div>
//             <ul className="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownInformationButton">
//                 <li >
//                     Dashboard
//                 </li>
//                 <li>
//                     Settings
//                 </li>
//                 <li>
//                     Earnings
//                 </li>
//             </ul>
//             <div className="py-2">
//                 <a href="#" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Sign out</a>
//             </div>
//         </div>
//     )}
// </div>



export default Dropdown;

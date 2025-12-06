import React from 'react';
import Footer from './Footer';
import Header from './Header';
import "../css/body.css";
const LayoutForm = ({children}) => {
    return (
        <>
        <Header/>
            {children}
            <Footer/>

        </>
    );
};

export default LayoutForm;
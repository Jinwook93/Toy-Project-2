import React from 'react';
import Footer from './Footer';
import Header from './Header';
import "../css/body.css";
//import "../css/container.css";

const LayoutForm = ({children}) => {
    return (
        <>
        <Header/>
        <div className ="container"> 
            {children}
            </div> 
            <Footer/>

        </>
    );
};

export default LayoutForm;
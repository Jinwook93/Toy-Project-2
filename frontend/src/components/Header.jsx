import React, { useState } from 'react';

const Header = (props) => {

    // const [isLogin, setIsLogin] = useState(false);
    // const [userName, setUserName] = useState("");

    // setIsLogin(props.isLogin);

    return (
        <div>
            <h2>안녕하세요, {props.isLogin ? props.username : "anonymousUser"}님</h2>
         
        </div>
    );
};

export default Header;
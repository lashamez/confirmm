import { notification } from 'antd';
import React from "react";
import 'antd/dist/antd.css';
import Link from "@material-ui/core/Link";

const openNotificationWithIcon = (type, title, description, href) => {
    notification[type]({
        message: title,
        description: description,
        onClick: ()=>{return (<Link to={href}/>)}
    });
};
export default function Notification(props) {
    return (
        <div>
            {openNotificationWithIcon(props.type, props.title, props.description, props.href)}
        </div>
    )
}

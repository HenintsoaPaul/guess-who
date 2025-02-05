import {useState,useEffect ,useRef} from "react";
import * as Device from "expo-device";
import * as Notifications from "expo-notifications";

import Constants from "expo-constants";

import { Platform } from "react-native";
import { fill } from "@cloudinary/url-gen/actions/resize";



export const usePushNotifications = () => {
    Notifications.setNotificationHandler({
        handleNotification:async()=> ({
            shouldPlaySound:false,
            shouldShowAlert:true,
            shouldSetBadge:false
        })
    });
    const [expoPushToken , setExpoPushToken] = useState<Notifications.ExpoPushToken | undefined>(null);


    const [notification , setNotification] = useState<Notifications.Notification | undefined>(null);

    const notificationListener = useRef<Notifications.Subscription | null>(null);
    const responseListener = useRef<Notifications.Subscription | null>(null);

    async function registerForPushNotificationAsync() {
        let token ;
        if(Device.isDevice){
            const {status, existingStatus}  = await Notifications.getPresentedNotificationsAsync();
            let finalStatus = existingStatus;

            if (existingStatus !== "granted") {
                const {status} = await Notifications.requestPermissionsAsync();
                finalStatus = status; 
            }
            if (finalStatus !== "granted") {
                alert("Failed to get push tokent");
            }

            token = await Notifications.getExpoPushTokenAsync({
                projectId: Constants.expoConfig?.extra?.eas?.projectId,
            });

            if(Platform.OS === 'android'){
                Notifications.setNotificationChannelAsync("default",{
                    name:"default",
                    importance : Notifications.AndroidImportance.MAX,
                    vibrationPattern:[0,250,250,250],
                    lightColor:"#FF231F7C"
                })
            }
            return token;
        }
        else {
            console.log("ERROR : Please use a physical device")
        }
    }

    useEffect(()=>{
        registerForPushNotificationAsync().then((token) => {
            setExpoPushToken(token);
        });

        notificationListener.current = 
            Notifications.addNotificationReceivedListener((notification)=>{
                setNotification(notification);
        });

        responseListener.current = 
            Notifications.addNotificationResponseReceivedListener((response) => {
                console.log(response);
        });

        return () => 
        {
            Notifications.removeNotificationSubscription(
                notificationListener.current
            );
            Notifications.removeNotificationSubscription(
                responseListener.current
            );
        }
    }, []);

    return {
        expoPushToken,
        notification
    }
}

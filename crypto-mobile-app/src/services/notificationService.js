import * as Notifications from "expo-notifications";




export async function askNotificationPermission(){
    let notifPermission = await Notifications.getPermissionsAsync();    
    if (notifPermission.status !== 'granted') {
        notifPermission = await Notifications.requestPermissionsAsync();
    }
    return notifPermission;
}

export async function getNotificationToken() {
    const notifPermission = await askNotificationPermission();    
    if (notifPermission.status === 'granted') {
        const token = await Notifications.getDevicePushTokenAsync();
        return token;
    }
    else {
        alert("Please allow notification on your settings");
    }
    return null;
}
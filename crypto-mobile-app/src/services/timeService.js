

export function getTimestampNow() {
    return new Date().toLocaleString('fr-FR', {
                    year: 'numeric', 
                    month: 'numeric', 
                    day: 'numeric', 
                    hour: 'numeric', 
                    minute: 'numeric', 
                    second: 'numeric', 
                    timeZone: 'Indian/Antananarivo',
                    timeZoneName:'short',
                })
}
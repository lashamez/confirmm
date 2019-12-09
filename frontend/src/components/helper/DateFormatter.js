class DateFormatter {
    static convertToString(localDate) {
        var monthNames = [
            "იანვარი", "თებერვალი", "მარტი",
            "აპრილი", "მაისი", "ივნისი", "ივლისი",
            "აგვისტო", "სექტემბერი", "ოქტომბერი",
            "ნოემბერი", "დეკემბერი"
        ];
        const day = localDate[2];
        const month = localDate[1];
        const year = localDate[0];
        return day + ' ' + monthNames[month-1] + ' ' + year
    }
    static convertToFieldString(date) {
        return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+(date.getDate())
    }
    static convertLocalDateToField(localDate) {
        return localDate[0] + "-" + localDate[1] + "-" + localDate[2]
    }
}
export default DateFormatter
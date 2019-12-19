const menus = [
    {
        stepNum : 0,
        title: 'პროექტის მენეჯმენტი',
        panelItems : [
            {
                id:0,
                label: "აუდიტის გუნდი",
                value :"auditTeam"
            },
            {
                id:1,
                label: "ტასკების გადანაწილება",
                value :"taskManagement"
            }
        ]
    },
    {
        stepNum : 1,
        title: 'რისკის მართვა და დაკონტრაქტება',
        panelItems:[
            {
                id:0,
                label: "კლიენტის ბექგროუნდი",
                value :"clientBackground"
            },
            {
                id:1,
                label: "კლიენტის რისკის შეფასება",
                value :"clientRiskRate"
            },
            {
                id:2,
                label: "ინტერესთა კონფლიქტი კომპანიის შიგნით",
                value :"interestConflict"
            },
            {
                id:3,
                label: "დამოუკიდებლობის დეკლარაცია",
                value :"independenceDeclaration"
            },
            {
                id:4,
                label: "ბიუჯეტირება",
                value :"budgeting"
            },
            {
                id:5,
                label: "პროექტის რისკის შეფასება",
                value :"projectRiskRate"
            },
            {
                id:6,
                label: "დაკონტრაქტება",
                value :"contracting"
            }
        ]
    },

    {
        stepNum : 2,
        title: 'პროექტის ძირითადი პრინციპები',
        panelItems : [
            {
                id:0,
                label: "სქოუფი",
                value :"scope"
            },
            {
                id:1,
                label: "სქეილინგი",
                value :"scaling"
            },
            {
                id:2,
                label: "პროექტის გუნდის შემადგენლობა",
                value :"projectTeam"
            }
        ]
    },
    {
        stepNum : 3,
        title: 'რისკის შეფასება',
        panelItems:[
            {
                id:0,
                label: "მთავარი პარტნიორისთვის",
                value :"mainForPartner"
            },
            {
                id:1,
                label: "მატერიალობა",
                value :"materiality"
            },
            {
                id:2,
                label: "მთავარი ანგარიშების განსაზღვრა",
                value :"mainAccountDetermination"
            },
            {
                id:3,
                label: "გეგმითი ანალიტიკური ანალიზი",
                value :"planarAnaliticalAnalysis"
            },
            {
                id:4,
                label: "ზოგადი რისკები",
                value :"generalRisks"
            },
            {
                id:5,
                label: "კომპანიის რისკები",
                value :"companyRisks"
            },
            {
                id:6,
                label: "კომპანიის შიდა კონტროლები",
                value :"companyInnerControl"
            },
            {
                id:7,
                label: "ჯგუფთან დაკავშირებული საკითხები",
                value :"groupRelatedIssues"
            },
            {
                id:8,
                label: "სხვა რისკის შეფასება (ჟურნალური გატარებები, რეპორტინგის კონტროლები)",
                value :"otherRisksEstimation"
            },
            {
                id:9,
                label: "პროცესები",
                value :"processes"
            },
            {
                id:10,
                label: "სპეციალისტები",
                value :"specialists"
            },
            {
                id:11,
                label: "რისკების შეფასების შეხვედრა",
                value :"riskEstimationMatch"
            },
        ]
    },
    {
        stepNum : 4,
        title: 'ტესტირება',
        panelItems:[
            {
                id:0,
                label: "კონტროლების ტესტირება",
                value :"controlTesting"
            },
            {
                id:1,
                label: "საბსთენთივ ტესტირება",
                value :"substantiveTesting"
            },
            {
                id:2,
                label: "საბსთენთივე თესთინგი არამთავარი ანგარიშები და სხვა ანგარიშგების ნაწილები",
                value :"substantiveTestingSecondaryAccounts"
            }
        ]
    },
    {
        stepNum : 5,
        title: 'პროექტის დასრულება',
        panelItems:[
            {
                id:0,
                label: "მატერიალობის გადაფასება",
                value :"materialityOverrate"
            },
            {
                id:1,
                label: "ზოგადი რისკების განახლება",
                value :"generalRiskUpdate"
            },
            {
                id:2,
                label: "ფინალური ანალიტიკა",
                value :"finalAnalytics"
            },
            {
                id:3,
                label: "რისკების შეფასების განახლება და საბოლოო მიმოხილვა",
                value :"riskEstimationAndFinalReview"
            },
            {
                id:4,
                label: "ფინანსური რეპორტინგის პროცესი",
                value :"financialReportingProcess"
            },
            {
                id:5,
                label: "დასრულება და ხელმოწერა",
                value :"completionAndSignature"
            }
        ]
    },
    {
        stepNum : 6,
        title: 'მუდმივი დოკუმენტაცია',
        panelItems:[

        ]
    }
]
export default menus
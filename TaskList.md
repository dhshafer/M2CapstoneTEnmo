Client Side
    * \Model
        User
            username
            password
            

    * \Services
        ConsoleServices
            all CLI print to screen
        Authentication
        AccountServices
            * Get Balance
            * Transfer Funds
            * Get History
            * See Transfer Details



Server Side
    * \ Controller
        AccountController
            GET
            POST
            PUT
            DELETE


    * \ Dao
        AccountDao
        JDBCAccountDao - interacts with DB
            * Get Balance
            * Transfer Funds
            * Get History
            * See Transfer Details


    * \ Exception


    *\ Model
        Account
            account balance
            history of transfers

    * \ Security
# Shopping Receipt
A program that prints a receipt for a shopping cart. 

## Input
The program accepts the file path of a JSON file which contains purchase details as input. The JSON should contain the location of purchase and an array of purchase items.  The table below list out the valid locations:

|  Location Name |Input  String |
| ------------ | ------------ |
|  New York |NY   |
|   California|  CA |

Sample JSON file of default use cases is available under `src/main/resources ` folder.
### JSON Format
    {
      "location": <string>,
      "items": [
        {
          "productName": <string>,
          "price": <number>,
          "quantity": <number>
        }
      ]
    }
	
#### Use Case 1 (Location: CA, 1 book at 17.99, 1 potato chips at 3.99)
    {
      "location": "CA",
      "items": [
        {
          "productName": "book",
          "price": 17.99,
          "quantity": 1
        },
        {
          "productName": "potato chips",
          "price": 3.99,
          "quantity": 1
        }
      ]
    }
	

#### Use Case 2 (Location: NY, 1 book at 17.99, 3 pencils at 2.99)
    {
      "location": "NY",
      "items": [
        {
          "productName": "book",
          "price": 17.99,
          "quantity": 1
        },
        {
          "productName": "pencil",
          "price": 2.99,
          "quantity": 3
        }
      ]
    }

#### Use Case 3 (Location: NY, 2 pencils at 2.99, 1 shirt at 29.99)
    {
      "location": "NY",
      "items": [
        {
          "productName": "pencil",
          "price": 2.99,
          "quantity": 2
        },
        {
          "productName": "shirt",
          "price": 29.99,
          "quantity": 1
        }
      ]
    }


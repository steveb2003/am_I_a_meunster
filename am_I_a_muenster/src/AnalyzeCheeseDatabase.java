import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;


public class AnalyzeCheeseDatabase
{
    public static void main(String[] args) 
    {
        String input_file = "cheese_data.csv"; //input file
        String output_file = "output.txt"; //output file

        try (BufferedReader cheese_reader = new BufferedReader(new FileReader(input_file));
         PrintWriter output_writer = new PrintWriter(new FileWriter(output_file)))
         {
            ArrayList<String> cheese_data = new ArrayList<>();
            String line;
            while ((line = cheese_reader.readLine()) != null)
            {
                cheese_data.add(line); //store csv in an array list by line
            }

            int pasteurized_number = pasteurized(cheese_data); 
            int raw_number= raw(cheese_data);

            int organic_and_moisture_number = organic_and_moisture(cheese_data);

            String most_common_milk = milk_source(cheese_data);

            output_writer.printf("Number of cheeses that use Pasteurized Milk: %d\n",pasteurized_number);
            output_writer.printf("Number of cheeses that use Raw Milk: %d\n", raw_number);
            output_writer.printf("Number of Organic cheeses that have a Moisture Percentage over 41: %d\n", organic_and_moisture_number);
            output_writer.printf("Animal that most cheeses come from in Canada: %s", most_common_milk);
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static int pasteurized(ArrayList<String> cheese_data)
    {
        int pasteurized_number = 0;
        for (int line=0; line < cheese_data.size(); line++)
        {
            if (cheese_data.get(line).contains("Pasteurized")) 
            {
                pasteurized_number+=1; //count lines of cheese that are pasteurized
            }
        }
        return pasteurized_number;
    }

    public static int raw(ArrayList<String> cheese_data)
    {
        int raw_number = 0;
        for (int line=0; line < cheese_data.size(); line++)
        {
            if (cheese_data.get(line).contains("Raw Milk"))
            {
                raw_number+=1; //count lines of cheese that are raw milk
            }
        }
        return raw_number;
    } 


    public static int organic_and_moisture(ArrayList<String> cheese_data) {
        int organic_and_moisture_number = 0;
        for (String line : cheese_data) {
            int start_moisture = 0;
            int commas = 0;
            for (int j = 0; j < line.length(); j++) {
                char element_index = line.charAt(j);
                if (element_index == ',') {
                    commas++;
                    if (commas == 3) {
                        start_moisture = j + 1;
                        break;
                    }
                }
            }
            try {
                String moisture_percentage_string = line.substring(start_moisture, start_moisture + 4).trim(); //grab the mositure percentage value after the third comma in each line from the csv
                double moisture_percentage = Double.parseDouble(moisture_percentage_string);
                if (moisture_percentage > 41.0 && line.contains(",1,")) { //if 42% of more and organic which is 1, add to the total
                    organic_and_moisture_number++;
                }
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) { //handle cases where substring is oit of bounds or does not have a valid amount of index
                System.out.println("Error parsing line: " + line);
                e.printStackTrace();
            }
        }
        return organic_and_moisture_number;
    }

    public static String milk_source(ArrayList<String> cheese_data)
    {
        int cow_count =0;
        int ewe_count =0;
        int goat_count = 0;
        int buffalo_count =0;
        for (int i=0; i < cheese_data.size(); i++)
        {
            String line = cheese_data.get(i);
            if (line.contains("Cow"))
            {
                cow_count +=1;
            }
            if (line.contains("Ewe"))
            {
                ewe_count+=1;
            }
            if (line.contains("Goat"))
            {
                goat_count+=1;
            }
            if (line.contains("Buffalo"))
            {
                buffalo_count+=1;
            }
            
        }
        ArrayList<Integer> compare_sources = new ArrayList<>(); //tally amount of milk from each animal
        compare_sources.add(cow_count);
        compare_sources.add(ewe_count);
        compare_sources.add(goat_count);
        compare_sources.add(buffalo_count);
        int max_index= 0;
        String most_common_milk = "";
        for (int i=0; i<compare_sources.size(); i++)
        {
            if (compare_sources.get(i)>max_index)
            {
                max_index=i;
            }
        }
//return the animal based on the index of the max count
        if (max_index==0)
        {
            most_common_milk = "Cow";
        }

        else if  (max_index==1)
        {
            most_common_milk = "Ewe";
        }

        else if (max_index ==2)
        {
            most_common_milk = "Goat";
        }

        else if (max_index ==3)

        {
            most_common_milk = "Buffalo";
        }
    return most_common_milk;
    }
}







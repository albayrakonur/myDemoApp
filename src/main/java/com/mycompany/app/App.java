package com.mycompany.app;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;


/**
 * Hello world!
 *
 */
public class App {
  public static double calculate(ArrayList<Integer> array,int ratio1, ArrayList<Integer> array2,int ratio2) {
    double result = 0;
    int summationOfArray = 0;
    int summationOfArray2 = 0;
    if(array == null || array2 == null){
      return -3;
    }else if(array.size() == 0 || array2.size() == 0) {
      return -1;
    }else if(ratio1 == 0 || ratio2 == 0) {
      return -2;
    }else if(ratio1 + ratio2 != 100) {
      return -4;
    }else {
    summationOfArray = getSumOfArrayList(array);
    summationOfArray2 = getSumOfArrayList(array2);
    int average_array = summationOfArray / array.size();
    int average_array2 = summationOfArray2 / array2.size();
    result = (average_array * ((double)ratio1/100)) + (average_array2 * ((double)ratio2/100));
    }
    
    return result;

  }

  private static int getSumOfArrayList(ArrayList<Integer> array) {
    int sum = 0;
    for(int num : array) {
      sum += num;
    }
    return sum;

  }

  public static void main(String[] args) {
      port(getHerokuAssignedPort());

      get("/", (req, res) -> "Hello, World");

      post("/compute", (req, res) -> {
        //System.out.println(req.queryParams("input1"));
        //System.out.println(req.queryParams("input2"));

        String input1 = req.queryParams("input1");
        java.util.Scanner sc1 = new java.util.Scanner(input1);
        sc1.useDelimiter("[;\r\n]+");
        java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
        while (sc1.hasNext())
        {
          int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
          inputList.add(value);
        }
        System.out.println(inputList);


        String input3 = req.queryParams("input3");
        java.util.Scanner sc2 = new java.util.Scanner(input3);
        sc2.useDelimiter("[;\r\n]+");
        java.util.ArrayList<Integer> inputList2 = new java.util.ArrayList<>();
        while (sc2.hasNext())
        {
          int value = Integer.parseInt(sc2.next().replaceAll("\\s",""));
          inputList2.add(value);
        }
        System.out.println(inputList2);

        String input2 = req.queryParams("input2").replaceAll("\\s","");
        int input2AsInt = Integer.parseInt(input2);
        System.out.println("input2: " + input2AsInt);


        String input4 = req.queryParams("input4").replaceAll("\\s","");
        int input4AsInt = Integer.parseInt(input4);
        System.out.println("input4: " + input4AsInt);

        double result = App.calculate(inputList, input2AsInt, inputList2, input4AsInt);

        Map map = new HashMap();
        map.put("result", result);
        return new ModelAndView(map, "compute.mustache");
      }, new MustacheTemplateEngine());


      get("/compute",
          (rq, rs) -> {
            Map map = new HashMap();
            map.put("result", "not computed yet!");
            return new ModelAndView(map, "compute.mustache");
          },
          new MustacheTemplateEngine());
  }

  static int getHerokuAssignedPort() {
      ProcessBuilder processBuilder = new ProcessBuilder();
      if (processBuilder.environment().get("PORT") != null) {
          return Integer.parseInt(processBuilder.environment().get("PORT"));
      }
      return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
  }
}
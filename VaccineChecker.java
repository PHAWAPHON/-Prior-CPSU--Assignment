
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class VaccineChecker {

    public static float calculateAge(LocalDate birthDate, LocalDate startDate, LocalDate endDate) {
        int startYears = Period.between(birthDate, startDate).getYears();
        int startMonths = Period.between(birthDate.plusYears(startYears), startDate).getMonths();
        int endYears = Period.between(birthDate, endDate).getYears();
        int endMonths = Period.between(birthDate.plusYears(endYears), endDate).getMonths();

        float ageStart = startYears + (startMonths / 12.0f);
        float ageEnd = endYears + (endMonths / 12.0f);
        float age = 0;
       // System.out.println(ageStart);
       // System.out.println(ageEnd);
         
        if (ageStart >= 65 || ageEnd >= 65) { // ถ้าageมากกว่าหรือเท่ากับ65ในช่วงวันที่ 1 มิถุนายน พ.ศ.2564 – 31 สิงหาคม พ.ศ.2564
            age = ageStart;
        } else {

            if ((ageStart < 65 || ageEnd < 65) &&(ageStart < 0.5 || ageEnd < 0.5)  ) { // age ไม่อยู่ในเงื่อนไข
                age = ageEnd;
            } else {
                if (ageStart <= 2 && ageStart >= 0.5) { // age อยู่ในอายุมากว่า 0.5 และน้อยกว่าหรือเท่ากับ 2
                    age = ageStart;
                    if (ageStart < 0.5 && (ageEnd <= 2 && ageEnd >= 0.5)) {  // ถ้า ageStart ยังไม่ถึง 6เดือน แต่ ageEnd อยู่ในอายุ 0.5 และน้อยกว่าหรือเท่ากับ 2
                        age = ageEnd;
                    }

                } 
            }

        }

        return age;
    }

    public static String[] checkEligibility(String gender, LocalDate birthDate) {
        LocalDate startDate = LocalDate.of(2564, 6, 1);
        LocalDate endDate = LocalDate.of(2564, 8, 31);

        String eligibleFlag = "N";
        String serviceStartDate = null;
        String serviceEndDate = null;

        float age = calculateAge(birthDate, startDate, endDate);
        //System.out.println(age);

        if ((gender.equals("Male") || gender.equals("Female")) && age >= 65) {
            eligibleFlag = "Y";
            serviceStartDate = startDate.toString();
            serviceEndDate = endDate.toString();
        } else if ((gender.equals("Male") || gender.equals("Female")) && (age >= 0.5 && age <= 2)) {
            eligibleFlag = "Y";
            LocalDate eligibleStartDate = birthDate.plusMonths(6);
            LocalDate eligibleEndDate = birthDate.plusYears(2);

            if (eligibleStartDate.isAfter(startDate)) {
                serviceStartDate = eligibleStartDate.toString();
            } else {
                serviceStartDate = startDate.toString();
            }

            if (eligibleEndDate.isBefore(endDate)) {
                serviceEndDate = eligibleEndDate.toString();
            } else {
                serviceEndDate = endDate.toString();
            }
        }

        return new String[] { eligibleFlag, serviceStartDate, serviceEndDate };
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String gender = "";
        boolean validGender = false;
        LocalDate dateOfBirth = null;
        boolean validDate = false;
        while (!validGender) {
            System.out.println("Enter gender (Male/Female): ");
            String input = scan.nextLine();

            if (input.equals("Male") || input.equals("Female")) {
                gender = input;
                validGender = true;
            } else {
                System.out.println("Invalid gender. Please enter 'Male' or 'Female'");
            }
        }

        while (!validDate) {
            try {
                System.out.println("Enter date of birth (yyyy-mm-dd): ");
                dateOfBirth = LocalDate.parse(scan.nextLine());
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter date in yyyy-mm-dd format");
            }
        }
        String[] eligibility = checkEligibility(gender, dateOfBirth);

        System.out.println("Eligible Flag: " + eligibility[0]);
        System.out.println("Service Start Date: " + eligibility[1]);
        System.out.println("Service End Date: " + eligibility[2]);
    }}
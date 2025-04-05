# Mobile Application Development
Weekly Reports for Mobile Application Development University Course.

This repository for small group projects from the **Mobile Application Development** course at the **University of Science - HCMUS**. We need to develop a new Android application each week based on the concepts we learn during that time. It serves as a collection of our weekly Android applications, showcasing our knowledge throughout the course.

## Student name

| Name |Student ID         | Github account                        |
|------|-------------------|---------------------------------------|
| Nguyễn Đặng Duy Mạnh    |   21200312   | [DuyManh122](https://github.com/DuyManh122) |


## Lab2 Projects
***Description:** Simple Calculator Android App*

## Demo
https://youtu.be/IdlnJZvjRGM


## Main Activity

![image](https://github.com/user-attachments/assets/92adda98-07e2-473f-9fd2-9712f4e2b182)


### Feature

```text
DrawerLayout
├── LinearLayout (Main Content)
│   ├── Toolbar
│   └── FrameLayout (Fragments)
└── NavigationView (Drawer)
```

- The NavigationView (inside DrawerLayout) allows users to switch between:
  + StandardCalculatorFragment (basic arithmetic).
  + ProgrammingCalculatorFragment (hex/dec/oct/bin operations).
- In xml: Using Drawer Layout to hold both the main content and the sliding drawer.
- The main content has two view:
  + Frame Layout: containing the view for the choosen fragment.
  + Toolbar: Integrating with DrawerLayout to display app title, navigation icon (☰), and menu (calculating mode) items.
- Using FragmentManager to dynamically replace between two calculating fragments when an item is selected.


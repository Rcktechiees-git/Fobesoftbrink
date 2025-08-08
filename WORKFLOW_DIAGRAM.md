# BrinkTest.java Workflow Diagram

```
                           ┌─────────────────────────────────────────┐
                           │         BrinkTest.java Workflow         │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │          Setup Phase (@BeforeMethod)    │
                           │  • Initialize download directories      │
                           │  • Setup Chrome WebDriver              │
                           │  • Configure file download preferences │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │         Read Excel Data Provider        │
                           │  • Load Scarlett.xlsx                  │
                           │  • Extract system URLs & credentials   │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
          ┌────────────────────────────────────────────────────────────────────────────────┐
          │                          BRINK POS SYSTEM DATA EXTRACTION                      │
          └────────────────────────────────────────────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │             Login to Brink              │
                           │  • Navigate to Brink URL               │
                           │  • Enter username & password           │
                           │  • Click "Continue" button             │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │          Navigate to Reports            │
                           │  • Click "Reports" menu                │
                           │  • Select "Sales And Labor Summary"    │
                           │  • Choose "By Location" option         │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │         Configure Report Settings       │
                           │  • Select "All Locations"              │
                           │  • Set date range to yesterday         │
                           │  • Click "View Report"                 │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │           Extract Financial Data        │
                           │  • Switch to report iframe             │
                           │  • Extract Net Sales Amount            │
                           │  • Extract Total Pay Amount            │
                           │  • Clean numeric values                │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
          ┌────────────────────────────────────────────────────────────────────────────────┐
          │                         FOBESOFT SYSTEM DATA ENTRY                            │
          └────────────────────────────────────────────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │            Login to FobeSoft            │
                           │  • Open new browser tab                │
                           │  • Navigate to FobeSoft URL            │
                           │  • Enter email & password              │
                           │  • Handle popup dialogs                │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │         Navigate to Yesterday's Date     │
                           │  • Calculate yesterday's date          │
                           │  • Click on date picker element        │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │            Update Revenue Data          │
                           │  • Double-click Food revenue field     │
                           │  • Clear existing value               │
                           │  • Enter Net Sales from Brink         │
                           │  • Save revenue update                │
                           │  • Wait for confirmation              │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │             Update Labor Data           │
                           │  • Navigate to Labor tab              │
                           │  • Double-click Hourly Labor field    │
                           │  • Clear existing value               │
                           │  • Enter Total Pay from Brink         │
                           │  • Save labor update                  │
                           │  • Wait for confirmation              │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │         Cleanup Phase (@AfterMethod)    │
                           │  • Quit WebDriver                      │
                           │  • Release browser resources           │
                           └─────────────────────────────────────────┘
                                              │
                                              ▼
                           ┌─────────────────────────────────────────┐
                           │              Test Complete              │
                           │  ✓ Data synchronized between systems   │
                           │  ✓ Yesterday's financials updated      │
                           └─────────────────────────────────────────┘

```

## Data Flow Summary

```
Excel File (Scarlett.xlsx)
        │
        ├── System URLs & Credentials
        │
        ▼
   Brink POS System                    FobeSoft System
        │                                    ▲
        ├── Net Sales Amount ──────────────┐  │
        │                                  │  │
        └── Total Pay Amount ──────────────┼──┘
                                           │
                                    Data Transfer
                                    (Revenue & Labor)
```

## Key Integration Points

1. **Authentication**: Both systems require separate login credentials
2. **Date Synchronization**: Both systems must use yesterday's date
3. **Data Mapping**: 
   - Brink Net Sales → FobeSoft Food Revenue
   - Brink Total Pay → FobeSoft Hourly Labor
4. **Validation**: Confirmation messages verify successful updates
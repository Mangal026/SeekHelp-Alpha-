#!/usr/bin/env python3
"""
AI Integration for Seek Help+ Desktop Application
Python backend for AI-powered medical assistance and symptom analysis

Author: Mangal Panday
Founder: Seek Help+ Medical Platform
"""

import json
import sys
import re
from datetime import datetime
from typing import Dict, List, Tuple, Optional
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class MedicalAI:
    """AI-powered medical assistance system"""
    
    def __init__(self):
        self.symptom_database = self._load_symptom_database()
        self.condition_database = self._load_condition_database()
        self.medication_database = self._load_medication_database()
        
    def _load_symptom_database(self) -> Dict:
        """Load symptom database from JSON"""
        return {
            "headache": {
                "severity_levels": ["mild", "moderate", "severe"],
                "associated_conditions": ["migraine", "tension", "cluster", "sinus"],
                "urgency_indicators": ["sudden severe", "with fever", "with confusion"]
            },
            "fever": {
                "severity_levels": ["low", "moderate", "high"],
                "associated_conditions": ["infection", "viral", "bacterial"],
                "urgency_indicators": ["above 103°F", "with rash", "with confusion"]
            },
            "chest_pain": {
                "severity_levels": ["mild", "moderate", "severe"],
                "associated_conditions": ["angina", "heart_attack", "acid_reflux"],
                "urgency_indicators": ["crushing pain", "radiating to arm", "with shortness of breath"]
            },
            "shortness_of_breath": {
                "severity_levels": ["mild", "moderate", "severe"],
                "associated_conditions": ["asthma", "pneumonia", "heart_failure"],
                "urgency_indicators": ["sudden onset", "with chest pain", "with blue lips"]
            },
            "abdominal_pain": {
                "severity_levels": ["mild", "moderate", "severe"],
                "associated_conditions": ["appendicitis", "gastritis", "food_poisoning"],
                "urgency_indicators": ["sudden severe", "with vomiting", "with fever"]
            }
        }
    
    def _load_condition_database(self) -> Dict:
        """Load medical conditions database"""
        return {
            "migraine": {
                "description": "Severe headache often accompanied by nausea and sensitivity to light",
                "symptoms": ["headache", "nausea", "light_sensitivity", "sound_sensitivity"],
                "urgency": "non_urgent",
                "recommendations": ["rest in dark room", "stay hydrated", "avoid triggers"]
            },
            "heart_attack": {
                "description": "Medical emergency requiring immediate attention",
                "symptoms": ["chest_pain", "shortness_of_breath", "nausea", "sweating"],
                "urgency": "emergency",
                "recommendations": ["call emergency immediately", "chew aspirin if available"]
            },
            "asthma": {
                "description": "Chronic respiratory condition causing breathing difficulties",
                "symptoms": ["shortness_of_breath", "wheezing", "coughing"],
                "urgency": "urgent",
                "recommendations": ["use inhaler", "sit upright", "call doctor if severe"]
            },
            "appendicitis": {
                "description": "Inflammation of appendix requiring surgical intervention",
                "symptoms": ["abdominal_pain", "nausea", "fever", "loss_of_appetite"],
                "urgency": "emergency",
                "recommendations": ["seek immediate medical attention", "do not eat or drink"]
            }
        }
    
    def _load_medication_database(self) -> Dict:
        """Load medication information database"""
        return {
            "paracetamol": {
                "generic_name": "Acetaminophen",
                "uses": ["fever", "pain", "headache"],
                "dosage": "500-1000mg every 4-6 hours",
                "side_effects": ["nausea", "liver_problems"],
                "precautions": ["do not exceed 4000mg daily", "avoid alcohol"]
            },
            "ibuprofen": {
                "generic_name": "Ibuprofen",
                "uses": ["pain", "inflammation", "fever"],
                "dosage": "200-400mg every 4-6 hours",
                "side_effects": ["stomach_upset", "kidney_problems"],
                "precautions": ["take with food", "avoid if stomach ulcers"]
            },
            "aspirin": {
                "generic_name": "Acetylsalicylic acid",
                "uses": ["pain", "fever", "heart_protection"],
                "dosage": "325-650mg every 4-6 hours",
                "side_effects": ["stomach_irritation", "bleeding_risk"],
                "precautions": ["avoid in children", "consult doctor before use"]
            }
        }
    
    def analyze_symptoms(self, symptoms: List[str], age: int = None, gender: str = None) -> Dict:
        """
        Analyze symptoms and provide AI-powered recommendations
        
        Args:
            symptoms: List of symptoms reported by user
            age: User's age (optional)
            gender: User's gender (optional)
            
        Returns:
            Dictionary containing analysis results
        """
        try:
            analysis = {
                "timestamp": datetime.now().isoformat(),
                "symptoms_analyzed": symptoms,
                "possible_conditions": [],
                "urgency_level": "non_urgent",
                "recommendations": [],
                "warnings": [],
                "confidence_score": 0.0
            }
            
            # Analyze each symptom
            condition_scores = {}
            urgency_indicators = []
            
            for symptom in symptoms:
                symptom_lower = symptom.lower().replace(" ", "_")
                
                if symptom_lower in self.symptom_database:
                    symptom_data = self.symptom_database[symptom_lower]
                    
                    # Check for urgency indicators
                    for indicator in symptom_data.get("urgency_indicators", []):
                        if indicator in symptom.lower():
                            urgency_indicators.append(f"{symptom}: {indicator}")
                    
                    # Score associated conditions
                    for condition in symptom_data.get("associated_conditions", []):
                        if condition not in condition_scores:
                            condition_scores[condition] = 0
                        condition_scores[condition] += 1
            
            # Determine possible conditions
            for condition, score in condition_scores.items():
                if score >= 1:  # At least one matching symptom
                    if condition in self.condition_database:
                        condition_data = self.condition_database[condition]
                        analysis["possible_conditions"].append({
                            "condition": condition,
                            "description": condition_data["description"],
                            "confidence": min(score * 0.3, 0.9),  # Max 90% confidence
                            "urgency": condition_data["urgency"],
                            "recommendations": condition_data["recommendations"]
                        })
            
            # Determine overall urgency level
            if urgency_indicators:
                analysis["urgency_level"] = "emergency"
                analysis["warnings"].extend(urgency_indicators)
            elif any(c["urgency"] == "emergency" for c in analysis["possible_conditions"]):
                analysis["urgency_level"] = "emergency"
            elif any(c["urgency"] == "urgent" for c in analysis["possible_conditions"]):
                analysis["urgency_level"] = "urgent"
            
            # Generate recommendations
            if analysis["urgency_level"] == "emergency":
                analysis["recommendations"].append("🚨 SEEK IMMEDIATE MEDICAL ATTENTION")
                analysis["recommendations"].append("Call emergency services immediately")
            elif analysis["urgency_level"] == "urgent":
                analysis["recommendations"].append("⚠️ Consult a healthcare provider within 24 hours")
            else:
                analysis["recommendations"].append("✅ Monitor symptoms and consult doctor if they persist")
            
            # Add general recommendations
            analysis["recommendations"].extend([
                "Stay hydrated",
                "Get adequate rest",
                "Avoid strenuous activities if experiencing symptoms"
            ])
            
            # Calculate confidence score
            if analysis["possible_conditions"]:
                max_confidence = max(c["confidence"] for c in analysis["possible_conditions"])
                analysis["confidence_score"] = max_confidence
            
            return analysis
            
        except Exception as e:
            logger.error(f"Error in symptom analysis: {e}")
            return {
                "error": "Analysis failed",
                "message": "Unable to analyze symptoms at this time"
            }
    
    def get_medication_info(self, medication_name: str) -> Dict:
        """
        Get detailed information about a medication
        
        Args:
            medication_name: Name of the medication
            
        Returns:
            Dictionary containing medication information
        """
        medication_lower = medication_name.lower()
        
        for med_name, med_data in self.medication_database.items():
            if med_name in medication_lower or med_data["generic_name"].lower() in medication_lower:
                return {
                    "medication": med_name,
                    "generic_name": med_data["generic_name"],
                    "uses": med_data["uses"],
                    "dosage": med_data["dosage"],
                    "side_effects": med_data["side_effects"],
                    "precautions": med_data["precautions"],
                    "disclaimer": "This information is for educational purposes only. Always consult a healthcare provider."
                }
        
        return {
            "error": "Medication not found",
            "message": f"No information available for {medication_name}"
        }
    
    def get_first_aid_advice(self, situation: str) -> Dict:
        """
        Provide first aid advice for common situations
        
        Args:
            situation: The emergency situation
            
        Returns:
            Dictionary containing first aid instructions
        """
        first_aid_database = {
            "bleeding": {
                "steps": [
                    "Apply direct pressure with clean cloth",
                    "Elevate the injured area if possible",
                    "Keep pressure for at least 10-15 minutes",
                    "Call emergency if bleeding is severe or doesn't stop"
                ],
                "do_not": [
                    "Remove embedded objects",
                    "Apply tourniquet unless trained",
                    "Clean deep wounds with hydrogen peroxide"
                ]
            },
            "burns": {
                "steps": [
                    "Cool the burn with running water for 10-20 minutes",
                    "Remove jewelry or tight items from burned area",
                    "Cover with sterile bandage",
                    "Do not pop blisters"
                ],
                "do_not": [
                    "Apply ice directly to burn",
                    "Use butter or oil",
                    "Break blisters"
                ]
            },
            "choking": {
                "steps": [
                    "Perform Heimlich maneuver if person is conscious",
                    "Give 5 back blows between shoulder blades",
                    "Give 5 abdominal thrusts",
                    "Call emergency if person becomes unconscious"
                ],
                "do_not": [
                    "Perform Heimlich on infants",
                    "Hit on the back if person is unconscious",
                    "Delay calling emergency"
                ]
            }
        }
        
        situation_lower = situation.lower()
        
        for key, advice in first_aid_database.items():
            if key in situation_lower:
                return {
                    "situation": key,
                    "steps": advice["steps"],
                    "do_not": advice["do_not"],
                    "emergency_call": "Call emergency services if condition worsens"
                }
        
        return {
            "error": "Situation not found",
            "message": f"No first aid information available for {situation}"
        }
    
    def chat_response(self, user_message: str) -> str:
        """
        Generate AI chat response for medical queries
        
        Args:
            user_message: User's message
            
        Returns:
            AI-generated response
        """
        user_message_lower = user_message.lower()
        
        # Check for emergency keywords
        emergency_keywords = ["emergency", "urgent", "severe", "critical", "immediate"]
        if any(keyword in user_message_lower for keyword in emergency_keywords):
            return ("🚨 If you're experiencing a medical emergency, please call emergency services immediately. "
                   "This AI assistant cannot provide emergency medical care.")
        
        # Check for symptom-related queries
        if any(word in user_message_lower for word in ["symptom", "pain", "fever", "headache"]):
            return ("I can help you understand symptoms, but I cannot provide a diagnosis. "
                   "Please consult with a healthcare provider for proper medical evaluation.")
        
        # Check for medication queries
        if any(word in user_message_lower for word in ["medicine", "medication", "drug", "pill"]):
            return ("I can provide general information about medications, but always consult "
                   "your doctor or pharmacist for specific advice about your medications.")
        
        # Default response
        return ("Thank you for your question. I'm here to provide general health information. "
               "For specific medical advice, diagnosis, or treatment, please consult a qualified healthcare provider.")

def main():
    """Main function for command-line testing"""
    if len(sys.argv) < 2:
        print("Usage: python ai_integration.py <command> [args...]")
        print("Commands:")
        print("  analyze <symptom1,symptom2,...> [age] [gender]")
        print("  medication <medication_name>")
        print("  firstaid <situation>")
        print("  chat <message>")
        return
    
    ai = MedicalAI()
    command = sys.argv[1]
    
    if command == "analyze":
        if len(sys.argv) < 3:
            print("Error: Please provide symptoms")
            return
        
        symptoms = sys.argv[2].split(",")
        age = int(sys.argv[3]) if len(sys.argv) > 3 else None
        gender = sys.argv[4] if len(sys.argv) > 4 else None
        
        result = ai.analyze_symptoms(symptoms, age, gender)
        print(json.dumps(result, indent=2))
    
    elif command == "medication":
        if len(sys.argv) < 3:
            print("Error: Please provide medication name")
            return
        
        medication = sys.argv[2]
        result = ai.get_medication_info(medication)
        print(json.dumps(result, indent=2))
    
    elif command == "firstaid":
        if len(sys.argv) < 3:
            print("Error: Please provide situation")
            return
        
        situation = sys.argv[2]
        result = ai.get_first_aid_advice(situation)
        print(json.dumps(result, indent=2))
    
    elif command == "chat":
        if len(sys.argv) < 3:
            print("Error: Please provide message")
            return
        
        message = sys.argv[2]
        result = ai.chat_response(message)
        print(result)
    
    else:
        print(f"Unknown command: {command}")

if __name__ == "__main__":
    main()

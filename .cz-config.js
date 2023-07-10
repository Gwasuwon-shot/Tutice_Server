const conventionalCommitTypes = require("cz-conventional-changelog");

module.exports = {
  // Other configuration options...

  // Customize commit message prompts
  messages: {
    type: "Select the type of change you are committing:",
    scope: "\nDenote the scope of this change (optional):",
    subject: "\nWrite a short description of the change (subject):",
    body: "Provide a longer description of the change (optional):\n",
    breaking: "List any breaking changes (optional):\n",
    footer: "List any additional issues or PR references (optional):\n",
    confirmCommit: "Are you sure you want to proceed with the commit above?",
  },

  // Specify the commit types and their corresponding symbols
  types: [
    { value: "feat", name: "🍊 feat: (sign language) implement new features" },
    { value: "feat", name: "🍋 feat: (Sojungiga) New feature implementation" },
    { value: "fix", name: "🐛 fix: fix bugs, errors" },
    { value: "chore", name: "🧹 chore: other changes that don't modify the src or test files" },
    { value: "refactor", name: "♻️ refactor: Code changes without bug fixes or feature additions" },
    { value: "build", name: "🏗️ build: changes that affect the build system or external dependencies" },
    { value: "perf", name: "📈 perf: code changes to improve performance" },
    { value: "test", name: "🧪 test: add test or edit previous test" },
    { value: "docs", name: "📝 docs: Revision of documents such as README and WIKI" },
    { value: "revert", name: "⏪️ revert: to revert a previous commit" },
    { value: "ci", name: "📦 ci: change CI configuration files and scripts" },
    { value: "merge", name: "🖇️ Merge: When merging another branch" },
    { value: "init", name: "📌 init: In case of initial commit" },
  ],

  // Other configuration options...
};
